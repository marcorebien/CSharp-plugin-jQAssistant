package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpMethodDescriptor {

    String getName();
    void setName(String name);

    String getSignature();
    void setSignature(String signature);

    String getReturnType();
    void setReturnType(String returnType);

    String getVisibility();
    void setVisibility(String visibility);

    // MethodModifiers (Flags) – Booleans
    Boolean isStatic();
    void setStatic(Boolean value);

    Boolean isAbstract();
    void setAbstract(Boolean value);

    Boolean isVirtual();
    void setVirtual(Boolean value);

    Boolean isOverride();
    void setOverride(Boolean value);

    Boolean isSealed();
    void setSealed(Boolean value);

    Boolean isAsync();
    void setAsync(Boolean value);

    // Optional: raw
    String getModifiersRaw();
    void setModifiersRaw(String raw);
}
