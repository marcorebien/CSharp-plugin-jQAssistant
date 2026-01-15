package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpFieldDescriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    String getVisibility();
    void setVisibility(String visibility);

    Boolean isStatic();
    void setStatic(Boolean value);

    // Optional: readonly wäre eher FieldModifier
    Boolean isReadOnly();
    void setReadOnly(Boolean value);
}
