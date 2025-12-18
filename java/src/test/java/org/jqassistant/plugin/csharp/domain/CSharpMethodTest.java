package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class CSharpMethodTest {

    @Test
    void shouldCreateMethodWithNameAndReturnType() {
        CSharpMethod method = new CSharpMethod("DoWork", "void");

        assertEquals("DoWork", method.getName());
        assertEquals("void", method.getReturnType());
        assertTrue(method.getParameters().isEmpty());
    }

    @Test
    void shouldAddParameters() {
        CSharpMethod method = new CSharpMethod("Add", "int");
        CSharpParameter p = new CSharpParameter("a", "int");

        method.addParameter(p);

        assertEquals(1, method.getParameters().size());
        assertEquals("a", method.getParameters().get(0).getName());
    }

    @Test
    void shouldAddMultipleParameters() {
        CSharpMethod method = new CSharpMethod("Test", "void");

        method.addParameter(new CSharpParameter("x", "int"));
        method.addParameter(new CSharpParameter("y", "string"));

        assertEquals(2, method.getParameters().size());
    }


    @Test
    void methodDefaultsAreCorrect() {
        CSharpMethod m = new CSharpMethod("Run", "void");

        assertEquals(CSharpVisibility.PRIVATE, m.getVisibility());
        assertFalse(m.isStatic());
        assertFalse(m.isAbstract());
    }

}
