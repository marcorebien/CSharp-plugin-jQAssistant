package org.jqassistant.plugin.csharp.domain.enums;

import java.util.Locale;

public enum CSharpVisibility {
    PUBLIC,
    PRIVATE,
    PROTECTED,
    INTERNAL,
    PROTECTED_INTERNAL;

    public static CSharpVisibility fromJson(String value) {
        if (value == null) return INTERNAL;
        String v = value.trim().toLowerCase(Locale.ROOT);
        return switch (v) {
            case "public" -> PUBLIC;
            case "private" -> PRIVATE;
            case "protected" -> PROTECTED;
            case "internal" -> INTERNAL;
            case "protectedinternal", "protected_internal", "protected internal" -> PROTECTED_INTERNAL;
            default -> INTERNAL;
        };
    }
}
