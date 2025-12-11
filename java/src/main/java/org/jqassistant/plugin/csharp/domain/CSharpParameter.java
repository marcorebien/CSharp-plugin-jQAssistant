package org.jqassistant.plugin.csharp.domain;

import java.util.Objects;

/**
 * Represents a method parameter in C#.
 * UML:
 * - name : String
 * - type : String
 */
public class CSharpParameter {

    private final String name;
    private String type;

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

    public void setType(String type) {
        this.type = Objects.requireNonNull(type, "Parameter type must not be null");
    }

    @Override
    public String toString() {
        return name + " : " + type;
    }
}
