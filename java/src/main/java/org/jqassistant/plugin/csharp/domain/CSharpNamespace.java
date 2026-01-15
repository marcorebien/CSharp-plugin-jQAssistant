package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpNamespace {
    private final String name;
    private final List<CSharpType> types = new ArrayList<>();

    public CSharpNamespace(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() { return name; }

    public List<CSharpType> getTypes() { return types; }

    public void addType(CSharpType type) {
        types.add(Objects.requireNonNull(type));
    }

    @Override
    public String toString() {
        return "CSharpNamespace{name='" + name + "', types=" + types + '}';
    }
}
