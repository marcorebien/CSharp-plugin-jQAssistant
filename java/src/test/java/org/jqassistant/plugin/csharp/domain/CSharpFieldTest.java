package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpFieldTest {

    @Test
    void shouldCreateFieldWithNameAndType() {
        CSharpField f = new CSharpField("count", "int");

        assertEquals("count", f.getName());
        assertEquals("int", f.getType());
    }

}
