package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

@Label("CSharpNamespace")
public interface CSharpNamespaceDescriptor extends Descriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Relation("DECLARES_TYPE")
    List<CSharpTypeDescriptor> getTypes();
}
