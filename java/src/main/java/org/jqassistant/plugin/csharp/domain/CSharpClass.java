package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# class with fields, properties, methods, a base class
 * and implemented interfaces.
 */
public class CSharpClass extends CSharpType {

    private String baseClass;
    private final List<String> interfaces = new ArrayList<>();
    private final List<CSharpMethod> methods = new ArrayList<>();
    private final List<CSharpField> fields = new ArrayList<>();
    private final List<CSharpProperty> properties = new ArrayList<>();

    public CSharpClass(String name, String namespace) {
        super(name, namespace);
    }

    // Convenience constructor for tests where namespace is irrelevant
    public CSharpClass(String name) {
        this(name, "");
    }

    public String getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void addInterface(String interfaceName) {
        Objects.requireNonNull(interfaceName, "interface name must not be null");
        interfaces.add(interfaceName);
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

    @Override
    public String toString() {
        return "CSharpClass{" +
                "name='" + getName() + '\'' +
                ", namespace='" + getNamespace() + '\'' +
                ", baseClass='" + baseClass + '\'' +
                ", interfaces=" + interfaces +
                ", methods=" + methods +
                ", fields=" + fields +
                ", properties=" + properties +
                '}';
    }
}
