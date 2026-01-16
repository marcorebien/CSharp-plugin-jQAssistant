package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;
import com.buschmais.xo.neo4j.api.annotation.Label;

import java.util.List;

@Label("CSharpMethod")
public interface CSharpMethodDescriptor extends Descriptor {

    String getName();
    void setName(String name);

    String getSignature();
    void setSignature(String signature);

    String getReturnType();
    void setReturnType(String returnType);

    CSharpVisibility getVisibility();
    void setVisibility(CSharpVisibility visibility);

    // Flags (passt sauber zum C# Exporter "modifiers":"static|async|virtual|...")
    Boolean isStatic();
    void setStatic(Boolean v);

    Boolean isAbstract();
    void setAbstract(Boolean v);

    Boolean isVirtual();
    void setVirtual(Boolean v);

    Boolean isOverride();
    void setOverride(Boolean v);

    Boolean isSealed();
    void setSealed(Boolean v);

    Boolean isAsync();
    void setAsync(Boolean v);

    @Relation("HAS_PARAMETER")
    List<CSharpParameterDescriptor> getParameters();
}