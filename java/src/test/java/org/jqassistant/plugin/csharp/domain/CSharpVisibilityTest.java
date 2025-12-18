package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpVisibilityTest {

    @Test
    void shouldContainAllCSharpVisibilities() {
        assertEquals(6, CSharpVisibility.values().length);
    }

}
