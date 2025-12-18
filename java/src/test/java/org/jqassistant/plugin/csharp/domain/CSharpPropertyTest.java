package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpPropertyTest {

    @Test
    void shouldCreateProperty() {
        CSharpProperty p = new CSharpProperty("Name", "string");

        assertEquals("Name", p.getName());
        assertEquals("string", p.getType());
    }

    @Test
    void shouldSupportGetterAndSetter() {
        CSharpProperty p = new CSharpProperty("Name", "string");

        p.setHasGetter(true);
        p.setHasSetter(false);

        assertTrue(p.hasGetter());
        assertFalse(p.hasSetter());
    }
}
