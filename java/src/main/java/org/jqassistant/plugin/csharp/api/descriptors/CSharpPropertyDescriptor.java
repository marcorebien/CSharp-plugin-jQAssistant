package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a C# property.
 */
@Label("CSharpProperty")
public interface CSharpPropertyDescriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Property("type")
    String getType();
    void setType(String type);

    @Property("hasGetter")
    Boolean hasGetter();
    void setHasGetter(Boolean value);

    @Property("hasSetter")
    Boolean hasSetter();
    void setHasSetter(Boolean value);
}
