package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("CSharpProperty")
public interface CSharpPropertyDescriptor extends Descriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    Boolean hasGetter();
    void setHasGetter(Boolean v);

    Boolean hasSetter();
    void setHasSetter(Boolean v);
}
