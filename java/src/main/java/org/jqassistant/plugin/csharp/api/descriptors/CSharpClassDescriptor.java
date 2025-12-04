package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a C# class.
 */
@Label("CSharpClass")
public interface CSharpClassDescriptor extends CSharpTypeDescriptor {

    @Property("isAbstract")
    Boolean isAbstract();
    void setAbstract(Boolean value);

    @Property("isStatic")
    Boolean isStatic();
    void setStatic(Boolean value);

    @Property("isSealed")
    Boolean isSealed();
    void setSealed(Boolean value);

    @Property("baseType")
    String getBaseType();
    void setBaseType(String baseType);
}
