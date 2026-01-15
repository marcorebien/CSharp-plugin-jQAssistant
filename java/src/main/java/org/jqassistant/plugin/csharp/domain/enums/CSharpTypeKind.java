package org.jqassistant.plugin.csharp.domain.enums;

public enum CSharpTypeKind {
    CLASS, INTERFACE, STRUCT, ENUM, RECORD;

    public static CSharpTypeKind fromJson(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing C# type kind");
        }

        String v = value.trim().toLowerCase();

        return switch (v) {
            case "class" -> CLASS;
            case "interface" -> INTERFACE;
            case "struct" -> STRUCT;
            case "enum" -> ENUM;
            case "record" -> RECORD;
            default -> throw new IllegalArgumentException("Unknown C# type kind: " + value);
        };
    }
}
