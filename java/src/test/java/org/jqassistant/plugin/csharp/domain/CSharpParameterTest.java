package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpParameterTest {

    @Test
    void shouldCreateParameterWithNameAndType() {
        CSharpParameter p = new CSharpParameter("x", "int");

        assertEquals("x", p.getName());
        assertEquals("int", p.getType());
        assertEquals(-1, p.getPosition());
        assertEquals(CSharpParameterModifier.NONE, p.getModifier());
        assertFalse(p.isOptional());
        assertNull(p.getDefaultValue());
    }

    @Test
    void shouldSetPosition() {
        CSharpParameter p = new CSharpParameter("x", "int");

        p.setPosition(0);

        assertEquals(0, p.getPosition());
    }

    @Test
    void shouldRejectNegativePosition() {
        CSharpParameter p = new CSharpParameter("x", "int");
        assertThrows(IllegalArgumentException.class, () -> p.setPosition(-1));
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

    @Test
    void shouldSupportOptionalWithDefaultValue() {
        CSharpParameter p = new CSharpParameter("x", "int");

        p.setOptional(true);
        p.setDefaultValue("42");

        assertTrue(p.isOptional());
        assertEquals("42", p.getDefaultValue());
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(NullPointerException.class, () -> new CSharpParameter(null, "int"));
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(NullPointerException.class, () -> new CSharpParameter("x", null));
    }
}
