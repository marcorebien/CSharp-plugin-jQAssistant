package org.jqassistant.plugin.csharp.domain.enums;

import java.util.List;

/**
 * Represents the visibility of a C# program element.
 */
public enum CSharpVisibility {
    PUBLIC,
    PRIVATE,
    PROTECTED,
    INTERNAL;

    public static CSharpVisibility fromString(String value) {
        return switch (value) {
            case "public" -> PUBLIC;
            case "protected" -> PROTECTED;
            case "internal" -> INTERNAL;
            case "private" -> PRIVATE;
            default -> INTERNAL; // C# default
        };
    }
}
