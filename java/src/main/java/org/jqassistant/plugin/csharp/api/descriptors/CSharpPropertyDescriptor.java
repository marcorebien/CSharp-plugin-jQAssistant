package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpPropertyDescriptor {

    String getName();
    void setName(String name);

    String getType();
    void setType(String type);

    String getVisibility();
    void setVisibility(String visibility);

    Boolean hasGetter();
    void setHasGetter(Boolean value);

    Boolean hasSetter();
    void setHasSetter(Boolean value);

    // Optional: static? (wenn du es aus Roslyn extrahierst)
    Boolean isStatic();
    void setStatic(Boolean value);
}
