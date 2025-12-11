package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Base descriptor for all C# types (classes, interfaces).
 */
@Label("CSharpType")
public interface CSharpTypeDescriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Property("fullName")
    String getFullName();
    void setFullName(String fullName);

    @Property("namespace")
    String getNamespace();
    void setNamespace(String namespace);

    @Property("visibility")
    String getVisibility();
    void setVisibility(String visibility);
}
