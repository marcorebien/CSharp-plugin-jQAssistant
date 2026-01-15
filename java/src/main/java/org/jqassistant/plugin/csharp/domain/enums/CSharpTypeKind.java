package org.jqassistant.plugin.csharp.domain.enums;

import java.util.Locale;

public enum CSharpTypeKind {
    CLASS,
    INTERFACE,
    STRUCT,
    ENUM,
    RECORD;

    public static CSharpTypeKind fromJson(String value) {
        if (value == null) return CLASS;
        String v = value.trim().toLowerCase(Locale.ROOT);
        return switch (v) {
            case "class" -> CLASS;
            case "interface" -> INTERFACE;
            case "struct" -> STRUCT;
            case "enum" -> ENUM;
            case "record" -> RECORD;
            default -> CLASS;
        };
    }
}
