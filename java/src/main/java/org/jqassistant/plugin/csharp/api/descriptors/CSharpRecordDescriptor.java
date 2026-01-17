package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

@Label("CSharpRecord")
public interface CSharpRecordDescriptor extends CSharpTypeDescriptor {

    @Relation("EXTENDS")
    CSharpTypeDescriptor getBaseType();
    void setBaseType(CSharpTypeDescriptor baseType);

    @Relation("IMPLEMENTS")
    List<CSharpInterfaceDescriptor> getImplementedInterfaces();
}
