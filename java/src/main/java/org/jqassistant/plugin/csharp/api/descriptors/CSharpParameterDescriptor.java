package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a parameter of a C# method.
 */
@Label("CSharpParameter")
public interface CSharpParameterDescriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Property("type")
    String getType();
    void setType(String type);

    @Property("position")
    Integer getPosition();
    void setPosition(Integer position);
}
