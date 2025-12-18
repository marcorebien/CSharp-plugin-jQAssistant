package org.jqassistant.plugin.csharp.domain.enums;

import java.util.Optional;

public enum CSharpModifier {
    ABSTRACT,
    STATIC,
    SEALED,
    VIRTUAL,
    OVERRIDE,
    ASYNC,
    READONLY;

    public static Optional<CSharpModifier> fromString(String value) {
        return switch (value) {
            case "abstract" -> Optional.of(ABSTRACT);
            case "static" -> Optional.of(STATIC);
            case "sealed" -> Optional.of(SEALED);
            case "virtual" -> Optional.of(VIRTUAL);
            case "override" -> Optional.of(OVERRIDE);
            case "async" -> Optional.of(ASYNC);
            case "readonly" -> Optional.of(READONLY);
            default -> Optional.empty();
        };
    }
}
