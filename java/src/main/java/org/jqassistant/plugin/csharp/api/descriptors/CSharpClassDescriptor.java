package org.jqassistant.plugin.csharp.api.descriptors;

public interface CSharpClassDescriptor extends CSharpTypeDescriptor {

    String getBaseType();          // baseClass
    void setBaseType(String baseType);
}
