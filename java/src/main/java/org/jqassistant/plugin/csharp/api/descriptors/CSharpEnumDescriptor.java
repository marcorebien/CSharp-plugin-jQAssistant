package org.jqassistant.plugin.csharp.api.descriptors;

import java.util.List;

public interface CSharpEnumDescriptor extends CSharpTypeDescriptor {

    List<String> getMembers();
    void setMembers(List<String> members);
}
