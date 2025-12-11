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
    void shouldReplaceFullParameterList() {
        CSharpMethod method = new CSharpMethod("Test", "void");

        method.setParameters(List.of(
                new CSharpParameter("x", "int"),
                new CSharpParameter("y", "string")
        ));

        assertEquals(2, method.getParameters().size());
    }
}
