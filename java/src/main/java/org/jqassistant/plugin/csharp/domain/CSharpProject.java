package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# project containing namespaces.
 */
public class CSharpProject {

    private String name;
    private String rootPath;

    private final List<CSharpNamespace> namespaces = new ArrayList<>();

    public CSharpProject() {
    }

    public CSharpProject(String name, String rootPath) {
        this.name = name;
        this.rootPath = rootPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; // may be null
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath; // may be null
    }

    public List<CSharpNamespace> getNamespaces() {
        return namespaces;
    }

    public void addNamespace(CSharpNamespace namespace) {
        Objects.requireNonNull(namespace, "namespace must not be null");
        namespaces.add(namespace);
    }

    @Override
    public String toString() {
        return "CSharpProject{" +
                "name='" + name + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", namespaces=" + namespaces +
                '}';
    }
}
