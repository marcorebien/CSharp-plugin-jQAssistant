package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("CSharpField")
public interface CSharpFieldDescriptor extends Descriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    CSharpVisibility getVisibility();
    void setVisibility(CSharpVisibility visibility);

    Boolean isStatic();
    void setStatic(Boolean v);
}
