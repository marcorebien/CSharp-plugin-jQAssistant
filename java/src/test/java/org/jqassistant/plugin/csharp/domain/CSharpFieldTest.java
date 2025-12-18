package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpFieldTest {

    @Test
    void shouldCreateField() {
        CSharpField f = new CSharpField("x", "int");

        assertEquals("x", f.getName());
        assertEquals("int", f.getType());
        assertEquals(CSharpVisibility.PRIVATE, f.getVisibility());
    }

    @Test
    void shouldSetVisibility() {
        CSharpField f = new CSharpField("x", "int");
        f.setVisibility(CSharpVisibility.PUBLIC);

        assertEquals(CSharpVisibility.PUBLIC, f.getVisibility());
    }
}
