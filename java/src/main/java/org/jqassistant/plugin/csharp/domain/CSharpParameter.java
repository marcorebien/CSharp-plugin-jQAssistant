package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpParameterModifier;

import java.util.Objects;

public class CSharpParameter {

    private final String name;
    private final String type;

    private CSharpParameterModifier modifier = CSharpParameterModifier.NONE;
    private boolean optional;
    private String defaultValue; // nullable

    public CSharpParameter(String name, String type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public String getName() { return name; }
    public String getType() { return type; }

    public CSharpParameterModifier getModifier() { return modifier; }
    public void setModifier(CSharpParameterModifier modifier) { this.modifier = Objects.requireNonNull(modifier); }

    public boolean isOptional() { return optional; }
    public void setOptional(boolean optional) { this.optional = optional; }

    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
}
