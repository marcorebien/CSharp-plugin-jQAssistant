package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpClass extends CSharpType {

    private String baseType;
    private final List<String> implementedInterfaces = new ArrayList<>();

    private final List<CSharpMethod> methods = new ArrayList<>();
    private final List<CSharpField> fields = new ArrayList<>();
    private final List<CSharpProperty> properties = new ArrayList<>();

    public CSharpClass(String name, String namespace) {
        super(name, namespace, CSharpTypeKind.CLASS);
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public List<String> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public void implementInterface(String interfaceName) {
        implementedInterfaces.add(Objects.requireNonNull(interfaceName));
    }

    public List<CSharpMethod> getMethods() {
        return methods;
    }

    public void addMethod(CSharpMethod method) {
        methods.add(Objects.requireNonNull(method));
    }

    public List<CSharpField> getFields() {
        return fields;
    }

    public void addField(CSharpField field) {
        fields.add(Objects.requireNonNull(field));
    }

    public List<CSharpProperty> getProperties() {
        return properties;
    }

    public void addProperty(CSharpProperty property) {
        properties.add(Objects.requireNonNull(property));
    }
}
