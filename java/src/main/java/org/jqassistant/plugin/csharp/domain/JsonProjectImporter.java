package org.jqassistant.plugin.csharp.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jqassistant.plugin.csharp.domain.dto.*;

import java.io.InputStream;

public class JsonProjectImporter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CSharpProject importProject(InputStream jsonStream) {
        try {
            JsonProjectDto dto =
                    objectMapper.readValue(jsonStream, JsonProjectDto.class);

            return mapProject(dto);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to import C# project JSON", e);
        }
    }

    private CSharpProject mapProject(JsonProjectDto dto) {
        CSharpProject project = new CSharpProject();

        if (dto.namespaces != null) {
            dto.namespaces.forEach(ns ->
                    project.addNamespace(mapNamespace(ns))
            );
        }

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
        return switch (dto.kind) {
            case "class" -> mapClass((JsonClassDto) dto);
            case "interface" -> mapInterface((JsonInterfaceDto) dto);
            default -> throw new IllegalArgumentException(
                    "Unsupported C# type kind: " + dto.kind
            );
        };
    }

    private CSharpClass mapClass(JsonClassDto dto) {
        CSharpClass cls = new CSharpClass(dto.name, dto.namespace);

        if (dto.modifiers != null) {
            dto.modifiers.forEach(cls::addModifier);
        }

        cls.setBaseClass(dto.baseClass);

        if (dto.interfaces != null) {
            dto.interfaces.forEach(cls::addInterface);
        }

        if (dto.methods != null) {
            dto.methods.forEach(m ->
                    cls.addMethod(mapMethod(m))
            );
        }

        if (dto.fields != null) {
            dto.fields.forEach(f ->
                    cls.addField(new CSharpField(f.name, f.type))
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

        if (dto.modifiers != null) {
            dto.modifiers.forEach(iface::addModifier);
        }

        if (dto.interfaces != null) {
            dto.interfaces.forEach(iface::addInterface);
        }

        if (dto.methods != null) {
            dto.methods.forEach(m ->
                    iface.addMethod(mapMethod(m))
            );
        }

        return iface;
    }

    private CSharpMethod mapMethod(JsonMethodDto dto) {
        CSharpMethod method = new CSharpMethod(dto.name, dto.returnType);

        if (dto.parameters != null) {
            dto.parameters.forEach(p ->
                    method.addParameter(new CSharpParameter(p.name, p.type))
            );
        }

        return method;
    }
}
