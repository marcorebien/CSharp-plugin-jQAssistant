package org.jqassistant.plugin.csharp.domain;

import java.util.Objects;

/**
 * Represents a C# property
 *
 */
public class CSharpProperty {

    private final String name;
    private final String type;

    private boolean hasGetter;
    private boolean hasSetter;
    private boolean isStatic;

    private CSharpVisibility visibility = CSharpVisibility.INTERNAL;
    private CSharpVisibility getterVisibility;
    private CSharpVisibility setterVisibility;

    public CSharpProperty(String name, String type) {
        this.name = Objects.requireNonNull(name, "Property name must not be null");
        this.type = Objects.requireNonNull(type, "Property type must not be null");
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

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public CSharpVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CSharpVisibility visibility) {
        this.visibility = Objects.requireNonNull(visibility, "visibility must not be null");
    }

    public CSharpVisibility getGetterVisibility() {
        return getterVisibility;
    }

    public void setGetterVisibility(CSharpVisibility getterVisibility) {
        this.getterVisibility = getterVisibility;
    }

    public CSharpVisibility getSetterVisibility() {
        return setterVisibility;
    }

    public void setSetterVisibility(CSharpVisibility setterVisibility) {
        this.setterVisibility = setterVisibility;
    }

    /**
     * Convenience method: auto-property = getter + setter present.
     */
    public boolean isAutoProperty() {
        return hasGetter && hasSetter;
    }
}
