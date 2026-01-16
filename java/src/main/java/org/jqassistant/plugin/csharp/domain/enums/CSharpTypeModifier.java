package org.jqassistant.plugin.csharp.domain.enums;

import java.util.EnumSet;
import java.util.List;

public enum CSharpTypeModifier {
    STATIC,
    ABSTRACT,
    SEALED;

    public static EnumSet<CSharpTypeModifier> fromJson(List<String> values) {
        EnumSet<CSharpTypeModifier> result =
                EnumSet.noneOf(CSharpTypeModifier.class);

        if (values == null) {
            return result;
        }

        for (String v : values) {
            switch (v) {
                case "static" -> result.add(STATIC);
                case "abstract" -> result.add(ABSTRACT);
                case "sealed" -> result.add(SEALED);
                default -> {
                    // bewusst ignorieren (future-proof)
                }
            }
        }

        return result;
    }
}
