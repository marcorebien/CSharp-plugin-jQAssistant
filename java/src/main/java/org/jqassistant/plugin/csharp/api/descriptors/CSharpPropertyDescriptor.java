package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpPropertyDescriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    Boolean hasGetter();
    void setHasGetter(Boolean value);

    Boolean hasSetter();
    void setHasSetter(Boolean value);
}
