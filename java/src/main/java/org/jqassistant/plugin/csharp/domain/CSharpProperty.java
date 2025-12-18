package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;

import java.util.Objects;

public class CSharpProperty {

    private final String name;
    private final String type;

    private boolean hasGetter;
    private boolean hasSetter;
    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;

    public CSharpProperty(String name, String type) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean hasGetter() {
        return hasGetter;
    }

    public void setHasGetter(boolean hasGetter) {
        this.hasGetter = hasGetter;
    }

    public boolean hasSetter() {
        return hasSetter;
    }

    public void setHasSetter(boolean hasSetter) {
        this.hasSetter = hasSetter;
    }

    public CSharpVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CSharpVisibility visibility) {
        this.visibility = Objects.requireNonNull(visibility);
    }
}