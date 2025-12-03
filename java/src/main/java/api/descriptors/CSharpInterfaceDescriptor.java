package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Descriptor representing a C# interface.
 */
@Label("CSharpInterface")
public interface CSharpInterfaceDescriptor extends CSharpTypeDescriptor {

    @Property("isStatic")
    Boolean isStatic();
    void setStatic(Boolean value);
}
