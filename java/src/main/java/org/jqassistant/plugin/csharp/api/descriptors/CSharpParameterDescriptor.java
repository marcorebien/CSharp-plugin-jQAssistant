package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.jqassistant.plugin.csharp.domain.enums.CSharpParameterModifier;
import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("CSharpParameter")
public interface CSharpParameterDescriptor extends Descriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    CSharpParameterModifier getModifier();
    void setModifier(CSharpParameterModifier modifier);

    Boolean isOptional();
    void setOptional(Boolean optional);

    String getDefaultValue();
    void setDefaultValue(String value);
}
