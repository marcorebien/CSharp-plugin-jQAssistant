package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# project containing one or more namespaces.
 * Matches UML: CSharpProject → namespaces : List<CSharpNamespace>
 */
public class CSharpProject {

    private final List<CSharpNamespace> namespaces = new ArrayList<>();

    public CSharpProject() {
        // nothing else needed, list initialized above
    }

    public List<CSharpNamespace> getNamespaces() {
        return namespaces;
    }

    /** Adds a namespace to the project. */
    public void addNamespace(CSharpNamespace namespace) {
        Objects.requireNonNull(namespace, "namespace must not be null");
        this.namespaces.add(namespace);
    }

    @Override
    public String toString() {
        return "CSharpProject{" +
                "namespaces=" + namespaces +
                '}';
    }
}
