package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# class declaration with semantic properties.
 */
public class CSharpClass extends CSharpType {

    private boolean isAbstract;
    private boolean isSealed;
    private boolean isStatic;

    private String baseType; // fully qualified name for now

    private final List<String> implementedInterfaces = new ArrayList<>();
    private final List<CSharpMethod> methods = new ArrayList<>();
    private final List<CSharpField> fields = new ArrayList<>();
    private final List<CSharpProperty> properties = new ArrayList<>();

    public CSharpClass(String name, String namespace) {
        super(name, namespace);
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean value) {
        this.isAbstract = value;
    }

    public boolean isSealed() {
        return isSealed;
    }

    public void setSealed(boolean value) {
        this.isSealed = value;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean value) {
        this.isStatic = value;
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
        Objects.requireNonNull(interfaceName, "interface name must not be null");
        implementedInterfaces.add(interfaceName);
    }

    public List<CSharpMethod> getMethods() {
        return methods;
    }

    public void addMethod(CSharpMethod method) {
        Objects.requireNonNull(method, "method must not be null");
        methods.add(method);
    }

    public List<CSharpField> getFields() {
        return fields;
    }

    public void addField(CSharpField field) {
        Objects.requireNonNull(field, "field must not be null");
        fields.add(field);
    }

    public List<CSharpProperty> getProperties() {
        return properties;
    }

    public void addProperty(CSharpProperty property) {
        Objects.requireNonNull(property, "property must not be null");
        properties.add(property);
    }
}
