package org.jqassistant.plugin.csharp.domain;

import java.util.Objects;

/**
 * Represents a C# property.
 *
 * UML:
 * - name : String
 * - type : String
 */
public class CSharpProperty {

    private final String name;
    private final String type;

    public CSharpProperty(String name, String type) {
        this.name = Objects.requireNonNull(name, "Property name must not be null");
        this.type = Objects.requireNonNull(type, "Property type must not be null");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " : " + type;
    }
}
