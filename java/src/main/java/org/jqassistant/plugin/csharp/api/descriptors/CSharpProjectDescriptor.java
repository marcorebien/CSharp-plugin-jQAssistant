package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

@Label("CSharpProject")
public interface CSharpProjectDescriptor extends Descriptor {

    String getName();
    void setName(String name);

    @Relation("CONTAINS_NAMESPACE")
    List<CSharpNamespaceDescriptor> getNamespaces();
}
