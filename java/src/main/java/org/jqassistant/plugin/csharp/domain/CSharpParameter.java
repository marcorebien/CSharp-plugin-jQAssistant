package org.jqassistant.plugin.csharp.domain;

import java.util.Objects;

/**
 * Represents a C# method parameter.
 */
public class CSharpParameter {

    private final String name;
    private final String type;

    private int position = -1;

    private CSharpParameterModifier modifier = CSharpParameterModifier.NONE;

    private boolean optional;
    private String defaultValue;

    public CSharpParameter(String name, String type) {
        this.name = Objects.requireNonNull(name, "Parameter name must not be null");
        this.type = Objects.requireNonNull(type, "Parameter type must not be null");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("position must be >= 0");
        }
        this.position = position;
    }

    public CSharpParameterModifier getModifier() {
        return modifier;
    }

    public void setModifier(CSharpParameterModifier modifier) {
        this.modifier = Objects.requireNonNull(modifier, "modifier must not be null");
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue; // may be null
    }
}
