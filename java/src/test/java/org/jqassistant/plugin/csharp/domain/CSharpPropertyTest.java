package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpPropertyTest {

    @Test
    void shouldCreatePropertyWithNameAndType() {
        CSharpProperty p = new CSharpProperty("Name", "string");

        assertEquals("Name", p.getName());
        assertEquals("string", p.getType());
    }
}
