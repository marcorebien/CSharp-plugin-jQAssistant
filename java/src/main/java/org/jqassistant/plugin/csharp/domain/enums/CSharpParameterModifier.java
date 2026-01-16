package org.jqassistant.plugin.csharp.domain.enums;

import java.util.Locale;

public enum CSharpParameterModifier {
    NONE,
    REF,
    OUT,
    IN,
    PARAMS;

    public static CSharpParameterModifier fromJson(String value) {
        if (value == null) return NONE;
        String v = value.trim().toLowerCase(Locale.ROOT);
        return switch (v) {
            case "ref" -> REF;
            case "out" -> OUT;
            case "in" -> IN;
            case "params" -> PARAMS;
            case "none" -> NONE;
            default -> NONE;
        };
    }
}
