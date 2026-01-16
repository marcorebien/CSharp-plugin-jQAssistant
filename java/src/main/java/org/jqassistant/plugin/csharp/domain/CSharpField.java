package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.Objects;

public class CSharpField {

    private final String name;
    private final String type;

    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;
    private boolean isStatic;

    public CSharpField(String name, String type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public String getName() { return name; }
    public String getType() { return type; }

    public CSharpVisibility getVisibility() { return visibility; }
    public void setVisibility(CSharpVisibility v) { this.visibility = Objects.requireNonNull(v); }

    public boolean isStatic() { return isStatic; }
    public void setStatic(boolean aStatic) { isStatic = aStatic; }
}
