package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a method in a C# type.
 */
@Label("CSharpMethod")
public interface CSharpMethodDescriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Property("returnType")
    String getReturnType();
    void setReturnType(String returnType);

    @Property("visibility")
    String getVisibility();
    void setVisibility(String visibility);

    @Property("isStatic")
    Boolean isStatic();
    void setStatic(Boolean value);

    @Property("isAbstract")
    Boolean isAbstract();
    void setAbstract(Boolean value);
}
