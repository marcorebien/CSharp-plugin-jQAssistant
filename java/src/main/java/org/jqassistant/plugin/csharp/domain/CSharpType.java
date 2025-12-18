package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for all C# types (classes, interfaces, etc.).
 */
public abstract class CSharpType {

    private final String name;
    private final String namespace;

    private CSharpVisibility visibility = CSharpVisibility.INTERNAL;
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

    /**
     * Returns the fully qualified C# type name.
     */
    public String getFullName() {
        return namespace.isEmpty() ? name : namespace + "." + name;
    }

    public CSharpVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CSharpVisibility visibility) {
        this.visibility = Objects.requireNonNull(visibility, "visibility must not be null");
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void addModifier(String modifier) {
        Objects.requireNonNull(modifier, "modifier must not be null");
        modifiers.add(modifier);
    }
}
