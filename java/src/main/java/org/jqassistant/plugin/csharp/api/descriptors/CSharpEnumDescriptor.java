package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

import java.util.List;

@Label("CSharpEnum")
public interface CSharpEnumDescriptor extends CSharpTypeDescriptor {

    @Property("members")
    List<String> getMembers();
    void setMembers(List<String> members);
}
