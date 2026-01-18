package org.jqassistant.plugin.csharp.api;

import com.buschmais.jqassistant.core.scanner.api.Scope;

public enum CSharpScope implements Scope {
    PROJECT;

    @Override
    public String getPrefix() {
        return "csharp";
    }

    @Override
    public String getName() {
        return "";
    }
}