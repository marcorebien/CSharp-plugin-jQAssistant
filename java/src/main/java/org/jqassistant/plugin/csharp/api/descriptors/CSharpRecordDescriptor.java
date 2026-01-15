package org.jqassistant.plugin.csharp.api.descriptors;


public interface CSharpRecordDescriptor extends CSharpTypeDescriptor {
    String getBaseType();
    void setBaseType(String baseType);
}