package org.jqassistant.plugin.csharp.api.descriptors;

import java.util.List;

public interface CSharpRecordDescriptor extends CSharpTypeDescriptor {

    String getBaseClass();
    void setBaseClass(String baseClass);

    List<String> getInterfaces();
    void setInterfaces(List<String> interfaces);
}
