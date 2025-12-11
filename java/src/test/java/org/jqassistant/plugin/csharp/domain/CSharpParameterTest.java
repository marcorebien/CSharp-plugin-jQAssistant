package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpParameterTest {

    @Test
    void shouldCreateParameterWithNameAndType() {
        CSharpParameter param = new CSharpParameter("value", "int");

        assertEquals("value", param.getName());
        assertEquals("int", param.getType());
    }

    @Test
    void shouldAllowChangingType() {
        CSharpParameter param = new CSharpParameter("value", "int");
        param.setType("string");

        assertEquals("string", param.getType());
    }
}
