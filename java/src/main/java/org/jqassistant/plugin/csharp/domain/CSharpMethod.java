package org.jqassistant.plugin.csharp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a C# method with name, return type and parameters.
*/
public class CSharpMethod {

    private final String name;
    private final String returnType;
    private final List<CSharpParameter> parameters = new ArrayList<>();

    public CSharpMethod(String name, String returnType) {
        this.name = Objects.requireNonNull(name, "Method name must not be null");
        this.returnType = Objects.requireNonNull(returnType, "Return type must not be null");
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<CSharpParameter> getParameters() {
        return parameters;
    }

    public void addParameter(CSharpParameter parameter) {
        Objects.requireNonNull(parameter, "parameter must not be null");
        parameters.add(parameter);
    }

    public void setParameters(List<CSharpParameter> params) {
        parameters.clear();
        if (params != null) {
            parameters.addAll(params);
        }
    }

    @Override
    public String toString() {
        return name + "(" + parameters + ") : " + returnType;
    }
}
