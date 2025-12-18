package org.jqassistant.plugin.csharp.domain.enums;

public enum CSharpParameterModifier {
    NONE,
    REF,
    OUT,
    IN,
    PARAMS;

    public static CSharpParameterModifier fromString(String value) {
        return switch (value) {
            case "ref" -> REF;
            case "out" -> OUT;
            case "in" -> IN;
            case "params" -> PARAMS;
            default -> throw new IllegalArgumentException(
                    "Unknown parameter modifier: " + value
            );
        };
    }
}


