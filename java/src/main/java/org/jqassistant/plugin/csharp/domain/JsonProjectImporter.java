package org.jqassistant.plugin.csharp.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jqassistant.plugin.csharp.domain.dto.*;
import org.jqassistant.plugin.csharp.domain.enums.*;

import java.io.InputStream;

public class JsonProjectImporter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CSharpProject importProject(InputStream jsonStream) {
        try {
            JsonProjectDto dto =
                    objectMapper.readValue(jsonStream, JsonProjectDto.class);
            return mapProject(dto);

        } catch (IllegalArgumentException e) {
            // semantische Fehler bewusst durchreichen
            throw e;

        } catch (Exception e) {
            // technische Fehler
            throw new IllegalStateException("Failed to import C# project JSON", e);
        }
    }

    private CSharpProject mapProject(JsonProjectDto dto) {
        CSharpProject project = new CSharpProject();

        if (dto.namespaces == null) {
            return project;
        }

        dto.namespaces.forEach(ns ->
                project.addNamespace(mapNamespace(ns))
        );

        return project;
    }

    private CSharpNamespace mapNamespace(JsonNamespaceDto dto) {
        CSharpNamespace namespace = new CSharpNamespace(dto.name);

        if (dto.types != null) {
            dto.types.forEach(t ->
                    namespace.addType(mapType(t))
            );
        }

        return namespace;
    }

    private CSharpType mapType(JsonTypeDto dto) {
        CSharpTypeKind kind = CSharpTypeKind.fromJson(dto.kind);

        return switch (kind) {
            case CLASS -> mapClass((JsonClassDto) dto);
            case INTERFACE -> mapInterface((JsonInterfaceDto) dto);
            default -> throw new IllegalArgumentException(
                    "Unsupported C# type kind: " + kind
            );
        };
    }

    private CSharpClass mapClass(JsonClassDto dto) {
        CSharpClass cls = new CSharpClass(dto.name, dto.namespace);

        if (dto.visibility != null) {
            cls.setVisibility(CSharpVisibility.fromString(dto.visibility));
        }

        if (dto.modifiers != null) {
            dto.modifiers.forEach(m ->
                    CSharpModifier.fromString(m).ifPresent(cls::addModifier)
            );
        }

        if (dto.baseClass != null) {
            cls.setBaseType(dto.baseClass);
        }

        if (dto.interfaces != null) {
            dto.interfaces.forEach(cls::implementInterface);
        }

        if (dto.methods != null) {
            dto.methods.forEach(m -> cls.addMethod(mapMethod(m)));
        }

        if (dto.fields != null) {
            dto.fields.forEach(f ->
                    cls.addField(mapField(f))
            );

        }

        if (dto.properties != null) {
            dto.properties.forEach(p ->
                    cls.addProperty(new CSharpProperty(p.name, p.type))
            );
        }

        return cls;
    }

    private CSharpInterface mapInterface(JsonInterfaceDto dto) {
        CSharpInterface iface = new CSharpInterface(dto.name, dto.namespace);

        if (dto.visibility != null) {
            iface.setVisibility(CSharpVisibility.fromString(dto.visibility));
        }

        if (dto.modifiers != null) {
            dto.modifiers.forEach(m ->
                    CSharpModifier.fromString(m).ifPresent(iface::addModifier)
            );
        }

        if (dto.interfaces != null) {
            dto.interfaces.forEach(iface::extendInterface);
        }

        if (dto.methods != null) {
            dto.methods.forEach(m -> iface.addMethod(mapMethod(m)));
        }

        return iface;
    }

    private CSharpMethod mapMethod(JsonMethodDto dto) {
        CSharpMethod method = new CSharpMethod(dto.name, dto.returnType);

        if (dto.visibility != null) {
            method.setVisibility(CSharpVisibility.fromString(dto.visibility));
        }

        if (dto.modifiers != null) {
            dto.modifiers.forEach(m ->
                    CSharpModifier.fromString(m).ifPresent(method::addModifier)
            );
        }

        if (dto.parameters != null) {
            dto.parameters.forEach(p ->
                    method.addParameter(new CSharpParameter(p.name, p.type))
            );
        }

        return method;
    }

    private CSharpProperty mapProperty(JsonPropertyDto dto) {
        CSharpProperty prop = new CSharpProperty(dto.name, dto.type);

        prop.setVisibility(CSharpVisibility.fromString(dto.visibility));
        prop.setHasGetter(dto.hasGetter);
        prop.setHasSetter(dto.hasSetter);

        return prop;
    }

    private CSharpParameter mapParameter(JsonParameterDto dto) {
        CSharpParameter param = new CSharpParameter(dto.name, dto.type);

        if (dto.modifier != null) {
            param.setModifier(CSharpParameterModifier.fromString(dto.modifier));
        }

        return param;
    }

    private CSharpField mapField(JsonFieldDto dto) {
        CSharpField field = new CSharpField(dto.name, dto.type);

        if (dto.visibility != null) {
            field.setVisibility(CSharpVisibility.fromString(dto.visibility));
        }

        if (dto.modifiers != null) {
            dto.modifiers.forEach(m ->
                    CSharpModifier.fromString(m)
                            .ifPresent(field::addModifier)
            );
        }

        return field;
    }


}
