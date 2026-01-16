package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class CSharpMethod {

    private final String name;
    private final String signature;
    private final String returnType;

    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;
    private EnumSet<CSharpMethodModifier> modifiers = EnumSet.noneOf(CSharpMethodModifier.class);

    private final List<CSharpParameter> parameters = new ArrayList<>();

    public CSharpMethod(String name, String signature, String returnType) {
        this.name = Objects.requireNonNull(name);
        this.signature = Objects.requireNonNull(signature);
        this.returnType = Objects.requireNonNull(returnType);
    }

    public String getName() { return name; }
    public String getSignature() { return signature; }
    public String getReturnType() { return returnType; }

    public CSharpVisibility getVisibility() { return visibility; }
    public void setVisibility(CSharpVisibility v) { this.visibility = Objects.requireNonNull(v); }

    public EnumSet<CSharpMethodModifier> getModifiers() { return modifiers; }
    public void setModifiers(EnumSet<CSharpMethodModifier> mods) { this.modifiers = Objects.requireNonNull(mods); }

    public List<CSharpParameter> getParameters() { return parameters; }
    public void addParameter(CSharpParameter p) { parameters.add(Objects.requireNonNull(p)); }

    @Override
    public String toString() {
        return signature + " [" + visibility + ", " + modifiers + "]";
    }
}
