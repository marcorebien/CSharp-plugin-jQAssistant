package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

@Label("CSharpEnum")
public interface CSharpEnumDescriptor extends CSharpTypeDescriptor {

    @Relation("DECLARES")
    List<CSharpEnumMemberDescriptor> getMembers();
}
