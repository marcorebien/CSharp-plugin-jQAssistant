package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a C# namespace.
 */
@Label("CSharpNamespace")
public interface CSharpNamespaceDescriptor {

    @Property("name")
    String getName();
    void setName(String name);
}
