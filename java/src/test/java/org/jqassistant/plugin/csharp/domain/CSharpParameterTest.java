package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpParameterModifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpParameterTest {

    @Test
    void shouldCreateParameter() {
        CSharpParameter p = new CSharpParameter("x", "int");

        assertEquals("x", p.getName());
        assertEquals("int", p.getType());
        assertEquals(CSharpParameterModifier.NONE, p.getModifier());
    }

    @Test
    void shouldSetModifier() {
        CSharpParameter p = new CSharpParameter("x", "int");

        p.setModifier(CSharpParameterModifier.REF);

        assertEquals(CSharpParameterModifier.REF, p.getModifier());
    }

    @Test
    void shouldRejectNullModifier() {
        CSharpParameter p = new CSharpParameter("x", "int");

        assertThrows(NullPointerException.class, () -> p.setModifier(null));
    }
}
