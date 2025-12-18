package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class CSharpField {

    private final String name;
    private final String type;

    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;
    private final Set<CSharpModifier> modifiers = EnumSet.noneOf(CSharpModifier.class);

    public CSharpField(String name, String type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void addModifier(CSharpModifier modifier) {
        modifiers.add(Objects.requireNonNull(modifier));
    }

    public boolean isStatic() {
        return modifiers.contains(CSharpModifier.STATIC);
    }

    public void setVisibility(CSharpVisibility cSharpVisibility) {
        visibility= cSharpVisibility;
    }

    public CSharpVisibility getVisibility() {
        return visibility;
    }

}
