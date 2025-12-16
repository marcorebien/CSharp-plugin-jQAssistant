package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for all C# types (classes, interfaces, structs, etc.).
*/
public abstract class CSharpType {

    private final String name;
    private final String namespace;
    private final List<String> modifiers = new ArrayList<>();

    protected CSharpType(String name, String namespace) {
        this.name = Objects.requireNonNull(name, "Type name must not be null");
        this.namespace = Objects.requireNonNull(namespace, "Namespace must not be null");
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    /** Adds a C# modifier (e.g., public, internal, abstract) */
    public void addModifier(String modifier) {
        Objects.requireNonNull(modifier, "modifier must not be null");
        modifiers.add(modifier);
    }

    @Override
    public String toString() {
        return "CSharpType{" +
                "name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                ", modifiers=" + modifiers +
                '}';
    }
}
