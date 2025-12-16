package org.jqassistant.plugin.csharp.domain;

import java.util.Objects;

/**
 * Represents a C# field.

 */
public class CSharpField {

    private final String name;
    private final String type;

    public CSharpField(String name, String type) {
        this.name = Objects.requireNonNull(name, "Field name must not be null");
        this.type = Objects.requireNonNull(type, "Field type must not be null");
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
