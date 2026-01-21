package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("CSharpEnumMember")
public interface CSharpEnumMemberDescriptor extends Descriptor {
    String getName();
    void setName(String name);
}
