package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpParameterDescriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    Integer getPosition();
    void setPosition(Integer position);

    String getModifier(); // "none/ref/out/in/params"
    void setModifier(String modifier);

    Boolean isOptional();
    void setOptional(Boolean optional);

    String getDefaultValue(); // nullable
    void setDefaultValue(String defaultValue);
}
