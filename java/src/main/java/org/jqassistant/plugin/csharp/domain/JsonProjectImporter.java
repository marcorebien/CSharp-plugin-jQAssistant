package org.jqassistant.plugin.csharp.domain;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jqassistant.plugin.csharp.domain.dto.*;
import org.jqassistant.plugin.csharp.domain.enums.*;

import java.io.InputStream;
import java.util.EnumSet;
import java.util.Objects;

/**
 * Imports the JSON exported by the C# analyzer (ProjectModel) into the Java domain model.
 *
 * Expected JSON shape (high level):
 * {
 *   "namespaces":[
 *     {
 *       "name":"...",
 *       "types":[
 *         {
 *           "type":"class|interface|type",
 *           "name":"...",
 *           "namespace":"...",
 *           "fullName":"...",
 *           "kind":"Class|Interface|Struct|Enum|Record",
 *           "visibility":"Public|...",
 *           "modifiers":"Static, Abstract" // string flags from exporter
 *           ...
 *         }
 *       ]
 *     }
 *   ]
 * }
 */
public class JsonProjectImporter {

    private final ObjectMapper objectMapper;

    public JsonProjectImporter() {
        this.objectMapper = new ObjectMapper()
                // be robust if C# adds fields later
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CSharpProject importProject(InputStream jsonStream) {
        Objects.requireNonNull(jsonStream, "jsonStream must not be null");

        try {
            JsonProjectDto dto = objectMapper.readValue(jsonStream, JsonProjectDto.class);
            return mapProject(dto);

        } catch (IllegalArgumentException e) {
            // semantic issues: pass through
            throw e;

        } catch (Exception e) {
            // technical issues
            throw new IllegalStateException("Failed to import C# project JSON", e);
        }
    }

    // -----------------------
    // Project / Namespace
    // -----------------------

    private CSharpProject mapProject(JsonProjectDto dto) {
        CSharpProject project = new CSharpProject();

        if (dto == null || dto.namespaces == null) {
            return project;
        }

        for (JsonNamespaceDto ns : dto.namespaces) {
            project.addNamespace(mapNamespace(ns));
        }
        return project;
    }

    private CSharpNamespace mapNamespace(JsonNamespaceDto dto) {
        String name = (dto != null && dto.name != null) ? dto.name : "<global>";
        CSharpNamespace ns = new CSharpNamespace(name);

        if (dto != null && dto.types != null) {
            for (JsonTypeDto t : dto.types) {
                ns.addType(mapType(t));
            }
        }

        return ns;
    }

    // -----------------------
    // Type dispatch
    // -----------------------

    private CSharpType mapType(JsonTypeDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Type DTO must not be null");
        }

        String discriminator = safeLower(dto.type);

        // Fast path für polymorphe DTOs (wenn vorhanden)
        if ("class".equals(discriminator)) {
            return mapClass((JsonClassDto) dto);
        }
        if ("interface".equals(discriminator)) {
            return mapInterface((JsonInterfaceDto) dto);
        }

        // ✅ kind zuerst, aber falls kind fehlt: discriminator als Fallback
        String kindValue = (dto.kind != null && !dto.kind.isBlank()) ? dto.kind : dto.type;
        CSharpTypeKind kind = CSharpTypeKind.fromJson(kindValue);

        return switch (kind) {
            case CLASS -> mapClass(cast(dto, JsonClassDto.class, "class"));
            case INTERFACE -> mapInterface(cast(dto, JsonInterfaceDto.class, "interface"));
            case STRUCT -> mapStruct(cast(dto, JsonStructDto.class, "struct"));
            case ENUM -> mapEnum(cast(dto, JsonEnumDto.class, "enum"));
            case RECORD -> mapRecord(cast(dto, JsonRecordDto.class, "record"));
        };
    }


    private static String safeLower(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    private static <T> T cast(JsonTypeDto dto, Class<T> type, String expected) {
        if (type.isInstance(dto)) return type.cast(dto);
        throw new IllegalArgumentException(
                "Type discriminator/kind mismatch. Expected DTO for '" + expected + "' but got: " + dto.getClass().getSimpleName()
        );
    }

    // -----------------------
    // Type mapping
    // -----------------------

    private void mapCommonTypeFields(JsonTypeDto dto, CSharpType target) {
        if (dto.visibility != null) {
            target.setVisibility(CSharpVisibility.fromJson(dto.visibility));
        }
        if (dto.modifiers != null) {
            EnumSet<CSharpTypeModifier> mods = CSharpTypeModifier.fromJson(dto.modifiers);
            target.setModifiers(mods);
        }
    }

    private CSharpClass mapClass(JsonClassDto dto) {
        requireTypeFields(dto);

        CSharpClass cls = new CSharpClass(dto.name, dto.namespace, dto.fullName);
        mapCommonTypeFields(dto, cls);

        cls.setBaseClass(dto.baseClass);

        if (dto.interfaces != null) {
            for (String i : dto.interfaces) {
                if (i != null) cls.addInterface(i);
            }
        }

        if (dto.fields != null) {
            for (JsonFieldDto f : dto.fields) {
                cls.addField(mapField(f));
            }
        }

        if (dto.properties != null) {
            for (JsonPropertyDto p : dto.properties) {
                cls.addProperty(mapProperty(p));
            }
        }

        if (dto.methods != null) {
            for (JsonMethodDto m : dto.methods) {
                cls.addMethod(mapMethod(m));
            }
        }

        return cls;
    }

    private CSharpInterface mapInterface(JsonInterfaceDto dto) {
        requireTypeFields(dto);

        CSharpInterface iface = new CSharpInterface(dto.name, dto.namespace, dto.fullName);
        mapCommonTypeFields(dto, iface);

        if (dto.interfaces != null) {
            for (String i : dto.interfaces) {
                if (i != null) iface.addInterface(i);
            }
        }

        if (dto.methods != null) {
            for (JsonMethodDto m : dto.methods) {
                iface.addMethod(mapMethod(m));
            }
        }

        return iface;
    }

    private CSharpStruct mapStruct(JsonStructDto dto) {
        requireTypeFields(dto);

        CSharpStruct st = new CSharpStruct(dto.name, dto.namespace, dto.fullName);
        mapCommonTypeFields(dto, st);

        if (dto.fields != null) {
            for (JsonFieldDto f : dto.fields) {
                st.addField(mapField(f));
            }
        }

        if (dto.properties != null) {
            for (JsonPropertyDto p : dto.properties) {
                st.addProperty(mapProperty(p));
            }
        }

        if (dto.methods != null) {
            for (JsonMethodDto m : dto.methods) {
                st.addMethod(mapMethod(m));
            }
        }

        return st;
    }

    private CSharpRecord mapRecord(JsonRecordDto dto) {
        requireTypeFields(dto);

        CSharpRecord rec = new CSharpRecord(dto.name, dto.namespace, dto.fullName);
        mapCommonTypeFields(dto, rec);

        rec.setBaseClass(dto.baseClass);

        if (dto.interfaces != null) {
            for (String i : dto.interfaces) {
                if (i != null) rec.addInterface(i);
            }
        }

        if (dto.fields != null) {
            for (JsonFieldDto f : dto.fields) {
                rec.addField(mapField(f));
            }
        }

        if (dto.properties != null) {
            for (JsonPropertyDto p : dto.properties) {
                rec.addProperty(mapProperty(p));
            }
        }

        if (dto.methods != null) {
            for (JsonMethodDto m : dto.methods) {
                rec.addMethod(mapMethod(m));
            }
        }

        return rec;
    }

    private CSharpEnum mapEnum(JsonEnumDto dto) {
        requireTypeFields(dto);

        CSharpEnum en = new CSharpEnum(dto.name, dto.namespace, dto.fullName);
        mapCommonTypeFields(dto, en);

        if (dto.members != null) {
            for (String m : dto.members) {
                if (m != null) en.addMember(m);
            }
        }

        return en;
    }

    private static void requireTypeFields(JsonTypeDto dto) {
        if (dto.name == null || dto.namespace == null || dto.fullName == null) {
            throw new IllegalArgumentException("Type must contain name/namespace/fullName. Got: name=" + dto.name
                    + ", namespace=" + dto.namespace + ", fullName=" + dto.fullName);
        }
    }

    // -----------------------
    // Member mapping
    // -----------------------

    private CSharpField mapField(JsonFieldDto dto) {
        if (dto == null) throw new IllegalArgumentException("Field DTO must not be null");
        CSharpField f = new CSharpField(dto.name, dto.type);

        if (dto.visibility != null) {
            f.setVisibility(CSharpVisibility.fromJson(dto.visibility));
        }
        f.setStatic(dto.isStatic);

        return f;
    }

    private CSharpProperty mapProperty(JsonPropertyDto dto) {
        if (dto == null) throw new IllegalArgumentException("Property DTO must not be null");

        CSharpProperty p = new CSharpProperty(dto.name, dto.type);
        p.setHasGetter(dto.hasGetter);
        p.setHasSetter(dto.hasSetter);
        return p;
    }

    private CSharpMethod mapMethod(JsonMethodDto dto) {
        if (dto == null) throw new IllegalArgumentException("Method DTO must not be null");

        // C# exports signature explicitly
        String sig = dto.signature != null ? dto.signature : dto.name + "()";
        CSharpMethod m = new CSharpMethod(dto.name, sig, dto.returnType);

        if (dto.visibility != null) {
            m.setVisibility(CSharpVisibility.fromJson(dto.visibility));
        }
        if (dto.modifiers != null) {
            m.setModifiers(CSharpMethodModifier.fromJson(dto.modifiers));
        }

        if (dto.parameters != null) {
            for (JsonParameterDto p : dto.parameters) {
                m.addParameter(mapParameter(p));
            }
        }

        return m;
    }

    private CSharpParameter mapParameter(JsonParameterDto dto) {
        if (dto == null) throw new IllegalArgumentException("Parameter DTO must not be null");

        CSharpParameter p = new CSharpParameter(dto.name, dto.type);

        if (dto.modifier != null) {
            p.setModifier(CSharpParameterModifier.fromJson(dto.modifier));
        }

        // optional/default from C# model
        p.setOptional(dto.optional);
        if (dto.defaultValue != null) {
            p.setDefaultValue(dto.defaultValue);
        }

        return p;
    }
}
