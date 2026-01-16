package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpProject {
    private final List<CSharpNamespace> namespaces = new ArrayList<>();

    public List<CSharpNamespace> getNamespaces() {
        return namespaces;
    }

    public void addNamespace(CSharpNamespace namespace) {
        namespaces.add(Objects.requireNonNull(namespace));
    }

    @Override
    public String toString() {
        return "CSharpProject{namespaces=" + namespaces + '}';
    }
}
