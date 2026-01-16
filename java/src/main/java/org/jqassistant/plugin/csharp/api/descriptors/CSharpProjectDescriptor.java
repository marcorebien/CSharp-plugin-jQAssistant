package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;

public interface CSharpProjectDescriptor extends Descriptor {
    String getName();
    void setName(String name);
}

