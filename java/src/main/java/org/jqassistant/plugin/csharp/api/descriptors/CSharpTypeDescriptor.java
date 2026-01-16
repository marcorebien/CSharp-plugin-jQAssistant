package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

import java.util.List;

@Label("CSharpType")
public interface CSharpTypeDescriptor extends Descriptor {

    @Property("name")
    String getName();
    void setName(String name);

    @Property("namespace")
    String getNamespace();
    void setNamespace(String namespace);

    @Property("fullName")
    String getFullName();
    void setFullName(String fullName);

    @Property("visibility")
    CSharpVisibility getVisibility();
    void setVisibility(CSharpVisibility visibility);

    @Property("kind")
    CSharpTypeKind getKind();
    void setKind(CSharpTypeKind kind);

    @Relation("DECLARES_METHOD")
    List<CSharpMethodDescriptor> getMethods();

    @Relation("DECLARES_FIELD")
    List<CSharpFieldDescriptor> getFields();

    @Relation("DECLARES_PROPERTY")
    List<CSharpPropertyDescriptor> getProperties();


    @Property("typeModifiers")
    String getTypeModifiers();
    void setTypeModifiers(String value);
}
