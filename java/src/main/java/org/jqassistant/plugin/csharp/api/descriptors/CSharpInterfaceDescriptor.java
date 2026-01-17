package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

@Label("CSharpInterface")
public interface CSharpInterfaceDescriptor extends CSharpTypeDescriptor {

    @Relation("EXTENDS")
    List<CSharpInterfaceDescriptor> getExtendedInterfaces();
}
