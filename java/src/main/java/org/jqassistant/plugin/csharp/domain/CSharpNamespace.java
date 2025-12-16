package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# namespace containing classes, interfaces, or other types.
*/
public class CSharpNamespace {

    private final String name;
    private final List<CSharpType> types = new ArrayList<>();

    public CSharpNamespace(String name) {
        this.name = Objects.requireNonNull(name, "Namespace name must not be null");
    }

    public String getName() {
        return name;
    }

    public List<CSharpType> getTypes() {
        return types;
    }

    /** Adds a class or interface (or any CSharpType subclass) to this namespace. */
    public void addType(CSharpType type) {
        Objects.requireNonNull(type, "type must not be null");
        this.types.add(type);
    }

    @Override
    public String toString() {
        return "CSharpNamespace{" +
                "name='" + name + '\'' +
                ", types=" + types +
                '}';
    }
}
