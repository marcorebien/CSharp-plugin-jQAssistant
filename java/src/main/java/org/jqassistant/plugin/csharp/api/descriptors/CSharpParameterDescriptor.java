package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpParameterDescriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    String getModifier();      // "none|ref|out|in|params"
    void setModifier(String modifier);

    Boolean isOptional();
    void setOptional(Boolean value);

    String getDefaultValue();
    void setDefaultValue(String value);
}
