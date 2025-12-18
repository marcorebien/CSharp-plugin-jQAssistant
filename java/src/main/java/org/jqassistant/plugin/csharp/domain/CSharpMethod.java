package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CSharpMethod {

    private final String name;
    private final String returnType;

    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;
    private final Set<CSharpModifier> modifiers = EnumSet.noneOf(CSharpModifier.class);
    private final List<CSharpParameter> parameters = new ArrayList<>();

    public CSharpMethod(String name, String returnType) {
        this.name = Objects.requireNonNull(name);
        this.returnType = Objects.requireNonNull(returnType);
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public CSharpVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CSharpVisibility visibility) {
        this.visibility = Objects.requireNonNull(visibility);
    }

    public Set<CSharpModifier> getModifiers() {
        return modifiers;
    }

    public void addModifier(CSharpModifier modifier) {
        modifiers.add(Objects.requireNonNull(modifier));
    }

    public List<CSharpParameter> getParameters() {
        return parameters;
    }

    public void addParameter(CSharpParameter parameter) {
        parameters.add(Objects.requireNonNull(parameter));
    }

    public void setParameters(List<CSharpParameter> params) {
        parameters.clear();
        if (params != null) {
            parameters.addAll(params);
        }
    }

    public boolean isStatic() {
        return modifiers.contains(CSharpModifier.STATIC);
    }

    public boolean isAbstract() {
        return modifiers.contains(CSharpModifier.ABSTRACT);
    }
}
