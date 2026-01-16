package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSharpStruct extends CSharpType {

    private final List<CSharpMethod> methods = new ArrayList<>();
    private final List<CSharpField> fields = new ArrayList<>();
    private final List<CSharpProperty> properties = new ArrayList<>();

    public CSharpStruct(String name, String namespace, String fullName) {
        super(name, namespace, fullName, CSharpTypeKind.STRUCT);
    }

    public List<CSharpMethod> getMethods() { return methods; }
    public void addMethod(CSharpMethod m) { methods.add(Objects.requireNonNull(m)); }

    public List<CSharpField> getFields() { return fields; }
    public void addField(CSharpField f) { fields.add(Objects.requireNonNull(f)); }

    public List<CSharpProperty> getProperties() { return properties; }
    public void addProperty(CSharpProperty p) { properties.add(Objects.requireNonNull(p)); }
}
