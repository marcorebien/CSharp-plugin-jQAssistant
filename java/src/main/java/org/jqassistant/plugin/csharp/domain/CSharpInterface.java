package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpInterface extends CSharpType {

    private final List<String> extendedInterfaces = new ArrayList<>();
    private final List<CSharpMethod> methods = new ArrayList<>();

    public CSharpInterface(String name, String namespace) {
        super(name, namespace, CSharpTypeKind.INTERFACE);
    }

    public List<String> getExtendedInterfaces() {
        return extendedInterfaces;
    }

    public void extendInterface(String name) {
        extendedInterfaces.add(Objects.requireNonNull(name));
    }

    public List<CSharpMethod> getMethods() {
        return methods;
    }

    public void addMethod(CSharpMethod method) {
        methods.add(Objects.requireNonNull(method));
    }
}
