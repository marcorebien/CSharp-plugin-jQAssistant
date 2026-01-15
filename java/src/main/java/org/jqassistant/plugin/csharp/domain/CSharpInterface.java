package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpInterface extends CSharpType {

    private final List<String> interfaces = new ArrayList<>();
    private final List<CSharpMethod> methods = new ArrayList<>();

    public CSharpInterface(String name, String namespace, String fullName) {
        super(name, namespace, fullName, CSharpTypeKind.INTERFACE);
    }

    public List<String> getInterfaces() { return interfaces; }

    public void addInterface(String iface) { interfaces.add(Objects.requireNonNull(iface)); }

    public List<CSharpMethod> getMethods() { return methods; }

    public void addMethod(CSharpMethod m) { methods.add(Objects.requireNonNull(m)); }
}
