package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpTypeDescriptor {

    String getName();
    void setName(String name);

    String getNamespace();
    void setNamespace(String namespace);

    String getFullName();
    void setFullName(String fullName);

    String getVisibility();
    void setVisibility(String visibility);

    CSharpTypeKind getKind();
    void setKind(CSharpTypeKind kind);

    Boolean isStatic();
    void setStatic(Boolean value);

    Boolean isAbstract();
    void setAbstract(Boolean value);

    Boolean isSealed();
    void setSealed(Boolean value);

    String getModifiersRaw();
    void setModifiersRaw(String raw);
}
