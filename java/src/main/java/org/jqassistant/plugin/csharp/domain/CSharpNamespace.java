package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# namespace containing types.
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

    /**
     * Returns the fully qualified namespace name.
     * (For now identical to name, but kept for symmetry with types.)
     */
    public String getFullName() {
        return name;
    }

    public List<CSharpType> getTypes() {
        return types;
    }

    public void addType(CSharpType type) {
        Objects.requireNonNull(type, "type must not be null");
        types.add(type);
    }
}
