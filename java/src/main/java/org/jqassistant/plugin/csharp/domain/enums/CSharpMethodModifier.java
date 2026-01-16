package org.jqassistant.plugin.csharp.domain.enums;

import java.util.EnumSet;
import java.util.Locale;

public enum CSharpMethodModifier {
    STATIC,
    ABSTRACT,
    VIRTUAL,
    OVERRIDE,
    SEALED,
    ASYNC;

    public static EnumSet<CSharpMethodModifier> fromJson(String value) {
        EnumSet<CSharpMethodModifier> set = EnumSet.noneOf(CSharpMethodModifier.class);
        if (value == null || value.isBlank() || "none".equalsIgnoreCase(value)) return set;

        for (String part : value.split(",")) {
            String p = part.trim().toLowerCase(Locale.ROOT);
            switch (p) {
                case "static" -> set.add(STATIC);
                case "abstract" -> set.add(ABSTRACT);
                case "virtual" -> set.add(VIRTUAL);
                case "override" -> set.add(OVERRIDE);
                case "sealed" -> set.add(SEALED);
                case "async" -> set.add(ASYNC);
                default -> { /* ignore */ }
            }
        }
        return set;
    }
}
