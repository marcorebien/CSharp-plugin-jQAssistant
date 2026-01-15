package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpEnum extends CSharpType {

    private final List<String> members = new ArrayList<>();

    public CSharpEnum(String name, String namespace, String fullName) {
        super(name, namespace, fullName, CSharpTypeKind.ENUM);
    }

    public List<String> getMembers() { return members; }

    public void addMember(String member) {
        members.add(Objects.requireNonNull(member));
    }
}
