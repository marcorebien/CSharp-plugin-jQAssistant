package org.jqassistant.plugin.csharp.api.descriptors;

import java.util.List;

public interface CSharpInterfaceDescriptor extends CSharpTypeDescriptor {

    List<String> getInterfaces();
    void setInterfaces(List<String> interfaces);
}
