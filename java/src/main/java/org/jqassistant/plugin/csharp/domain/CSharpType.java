package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;
import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.EnumSet;
import java.util.Objects;

public abstract class CSharpType {

    private final String name;
    private final String namespace;
    private final String fullName;

    private final CSharpTypeKind kind;

    private CSharpVisibility visibility = CSharpVisibility.INTERNAL;
    private EnumSet<CSharpTypeModifier> modifiers = EnumSet.noneOf(CSharpTypeModifier.class);

    protected CSharpType(String name, String namespace, String fullName, CSharpTypeKind kind) {
        this.name = Objects.requireNonNull(name);
        this.namespace = Objects.requireNonNull(namespace);
        this.fullName = Objects.requireNonNull(fullName);
        this.kind = Objects.requireNonNull(kind);
    }

    public String getName() { return name; }

    public String getNamespace() { return namespace; }

    public String getFullName() { return fullName; }

    public CSharpTypeKind getKind() { return kind; }

    public CSharpVisibility getVisibility() { return visibility; }

    public void setVisibility(CSharpVisibility visibility) {
        this.visibility = Objects.requireNonNull(visibility);
    }

    public EnumSet<CSharpTypeModifier> getModifiers() { return modifiers; }

    public void setModifiers(EnumSet<CSharpTypeModifier> modifiers) {
        this.modifiers = Objects.requireNonNull(modifiers);
    }

    @Override
    public String toString() {
        return kind + " " + fullName + " (" + visibility + ", " + modifiers + ")";
    }
}
