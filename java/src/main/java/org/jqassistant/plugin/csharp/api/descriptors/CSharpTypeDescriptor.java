package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import org.jqassistant.plugin.csharp.api.model.CSharpTypeKind;
import org.jqassistant.plugin.csharp.api.model.CSharpTypeModifier;
import org.jqassistant.plugin.csharp.api.model.CSharpVisibility;

import java.util.Set;

public interface CSharpTypeDescriptor extends Descriptor {

    String getName();
    void setName(String name);

    String getNamespace();
    void setNamespace(String namespace);

    String getFullName();
    void setFullName(String fullName);

    CSharpVisibility getVisibility();
    void setVisibility(CSharpVisibility visibility);

    CSharpTypeKind getKind();
    void setKind(CSharpTypeKind kind);

    Set<CSharpTypeModifier> getModifiers();
    void setModifiers(Set<CSharpTypeModifier> modifiers);
}
