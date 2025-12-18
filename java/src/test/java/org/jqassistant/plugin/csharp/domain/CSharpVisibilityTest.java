package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpVisibilityTest {

    @Test
    void shouldContainAllCSharpVisibilities() {
        assertEquals(6, CSharpVisibility.values().length);
    }

    @Test
    void shouldContainProtectedInternal() {
        assertNotNull(CSharpVisibility.PROTECTED_INTERNAL);
    }
}
