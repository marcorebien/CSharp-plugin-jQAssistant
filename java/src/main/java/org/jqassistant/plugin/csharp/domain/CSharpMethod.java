package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# method with semantic properties.
 */
public class CSharpMethod {

    private final String name;
    private final String returnType;

    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;

    private boolean isStatic;
    private boolean isAbstract;
    private boolean isVirtual;
    private boolean isOverride;
    private boolean isAsync;

    private final List<CSharpParameter> parameters = new ArrayList<>();

    public CSharpMethod(String name, String returnType) {
        this.name = Objects.requireNonNull(name, "method name must not be null");
        this.returnType = Objects.requireNonNull(returnType, "return type must not be null");
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
        this.visibility = Objects.requireNonNull(visibility, "visibility must not be null");
    }


    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean value) {
        this.isStatic = value;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean value) {
        this.isAbstract = value;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean value) {
        this.isVirtual = value;
    }

    public boolean isOverride() {
        return isOverride;
    }

    public void setOverride(boolean value) {
        this.isOverride = value;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean value) {
        this.isAsync = value;
    }

    public List<CSharpParameter> getParameters() {
        return parameters;
    }

    public void addParameter(CSharpParameter parameter) {
        Objects.requireNonNull(parameter, "parameter must not be null");
        parameters.add(parameter);
    }

}
