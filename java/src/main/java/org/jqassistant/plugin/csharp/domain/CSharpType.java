package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract base class for all C# types (classes, interfaces, etc.).
 */
public abstract class CSharpType {

    private final String name;
    private final String namespace;
    private final CSharpTypeKind kind;

    private CSharpVisibility visibility = CSharpVisibility.INTERNAL;
    private final Set<CSharpModifier> modifiers = EnumSet.noneOf(CSharpModifier.class);

    protected CSharpType(String name, String namespace, CSharpTypeKind kind) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.namespace = Objects.requireNonNull(namespace, "namespace must not be null");
        this.kind = Objects.requireNonNull(kind, "kind must not be null");
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public CSharpTypeKind getKind() {
        return kind;
    }

    public String getFullName() {
        return namespace.isEmpty() ? name : namespace + "." + name;
    }

    public CSharpVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CSharpVisibility visibility) {
        this.visibility = Objects.requireNonNull(visibility);
    }

    public Set<CSharpModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(CSharpModifier modifier) {
        modifiers.add(Objects.requireNonNull(modifier));
    }

    /* --- Convenience semantics --- */

    public boolean isAbstract() {
        return modifiers.contains(CSharpModifier.ABSTRACT);
    }

    public boolean isStatic() {
        return modifiers.contains(CSharpModifier.STATIC);
    }

    public boolean isSealed() {
        return modifiers.contains(CSharpModifier.SEALED);
    }
}
