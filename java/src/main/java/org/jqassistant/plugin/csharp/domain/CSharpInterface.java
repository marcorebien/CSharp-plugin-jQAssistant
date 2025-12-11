package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# interface with methods and extended interfaces.
 *
 * UML:
 * - inherits CSharpType
 * - interfaces : List<String>
 * - methods : List<CSharpMethod>
 */
public class CSharpInterface extends CSharpType {

    private final List<String> interfaces = new ArrayList<>();
    private final List<CSharpMethod> methods = new ArrayList<>();

    public CSharpInterface(String name, String namespace) {
        super(name, namespace);
    }

    // Convenience constructor for tests
    public CSharpInterface(String name) {
        this(name, "");
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void addInterface(String interfaceName) {
        Objects.requireNonNull(interfaceName, "interface name must not be null");
        interfaces.add(interfaceName);
    }

    public List<CSharpMethod> getMethods() {
        return methods;
    }

    public void addMethod(CSharpMethod method) {
        Objects.requireNonNull(method, "method must not be null");
        methods.add(method);
    }

    @Override
    public String toString() {
        return "CSharpInterface{" +
                "name='" + getName() + '\'' +
                ", namespace='" + getNamespace() + '\'' +
                ", interfaces=" + interfaces +
                ", methods=" + methods +
                '}';
    }
}
