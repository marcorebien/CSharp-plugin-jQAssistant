package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpParameterModifier;

import java.util.Objects;

public class CSharpParameter {

    private final String name;
    private final String type;
    private CSharpParameterModifier modifier;

    public CSharpParameter(String name, String type) {
        this(name, type, CSharpParameterModifier.NONE);
    }

    public CSharpParameter(String name, String type, CSharpParameterModifier modifier) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.modifier = Objects.requireNonNull(modifier);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setModifier(CSharpParameterModifier modifier) {
        this.modifier = Objects.requireNonNull(modifier);
    }

    public CSharpParameterModifier getModifier() {
        return modifier;
    }
}
