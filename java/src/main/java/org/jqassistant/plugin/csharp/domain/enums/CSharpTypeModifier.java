package org.jqassistant.plugin.csharp.domain.enums;

import java.util.EnumSet;
import java.util.Locale;

public enum CSharpTypeModifier {
    STATIC,
    ABSTRACT,
    SEALED;

    public static EnumSet<CSharpTypeModifier> fromJson(String value) {
        EnumSet<CSharpTypeModifier> set = EnumSet.noneOf(CSharpTypeModifier.class);
        if (value == null || value.isBlank() || "none".equalsIgnoreCase(value)) return set;

        // C# exporter liefert z.B. "Static, Abstract"
        for (String part : value.split(",")) {
            String p = part.trim().toLowerCase(Locale.ROOT);
            switch (p) {
                case "static" -> set.add(STATIC);
                case "abstract" -> set.add(ABSTRACT);
                case "sealed" -> set.add(SEALED);
                default -> { /* ignore unknown */ }
            }
        }
        return set;
    }
}
