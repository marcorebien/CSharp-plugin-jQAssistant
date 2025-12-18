package org.jqassistant.plugin.csharp.domain;

import java.util.Objects;

/**
 * Represents a C# field with semantic properties.
 */
public class CSharpField {

    private final String name;
    private final String type;

    private CSharpVisibility visibility = CSharpVisibility.PRIVATE;

    private boolean isStatic;
    private boolean isReadonly;
    private boolean isConst;

    public CSharpField(String name, String type) {
        this.name = Objects.requireNonNull(name, "Field name must not be null");
        this.type = Objects.requireNonNull(type, "Field type must not be null");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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

    public boolean isReadonly() {
        return isReadonly;
    }

    public void setReadonly(boolean value) {
        this.isReadonly = value;
    }

    public boolean isConst() {
        return isConst;
    }

    public void setConst(boolean value) {
        this.isConst = value;
    }

}
