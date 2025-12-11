package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a C# field.
 */
@Label("CSharpField")
public interface CSharpFieldDescriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Property("type")
    String getType();
    void setType(String type);

    @Property("visibility")
    String getVisibility();
    void setVisibility(String visibility);

    @Property("isStatic")
    Boolean isStatic();
    void setStatic(Boolean value);
}
