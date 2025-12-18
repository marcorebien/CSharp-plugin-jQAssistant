package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpMethodTest {

    @Test
    void shouldCreateMethod() {
        CSharpMethod m = new CSharpMethod("Run", "void");

        assertEquals("Run", m.getName());
        assertEquals("void", m.getReturnType());
        assertEquals(CSharpVisibility.PRIVATE, m.getVisibility());
    }

    @Test
    void shouldAddParameter() {
        CSharpMethod m = new CSharpMethod("Run", "void");

        m.addParameter(new CSharpParameter("x", "int"));

        assertEquals(1, m.getParameters().size());
    }

    @Test
    void shouldAddModifier() {
        CSharpMethod m = new CSharpMethod("Run", "void");

        m.addModifier(CSharpModifier.STATIC);

        assertTrue(m.getModifiers().contains(CSharpModifier.STATIC));
    }
}
