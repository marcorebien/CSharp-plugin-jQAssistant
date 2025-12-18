package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpPropertyTest {

    @Test
    void shouldCreatePropertyWithNameAndType() {
        CSharpProperty property = new CSharpProperty("Name", "string");

        assertEquals("Name", property.getName());
        assertEquals("string", property.getType());
    }

    @Test
    void shouldDefaultToInternalVisibility() {
        CSharpProperty property = new CSharpProperty("Name", "string");

        assertEquals(CSharpVisibility.INTERNAL, property.getVisibility());
    }

    @Test
    void shouldAllowSettingGetterAndSetterFlags() {
        CSharpProperty property = new CSharpProperty("Age", "int");

        property.setHasGetter(true);
        property.setHasSetter(false);

        assertTrue(property.hasGetter());
        assertFalse(property.hasSetter());
    }

    @Test
    void shouldDetectAutoPropertyWhenGetterAndSetterPresent() {
        CSharpProperty property = new CSharpProperty("Age", "int");

        property.setHasGetter(true);
        property.setHasSetter(true);

        assertTrue(property.isAutoProperty());
    }

    @Test
    void shouldNotDetectAutoPropertyWhenOnlyGetterPresent() {
        CSharpProperty property = new CSharpProperty("Age", "int");

        property.setHasGetter(true);
        property.setHasSetter(false);

        assertFalse(property.isAutoProperty());
    }

    @Test
    void shouldAllowStaticProperty() {
        CSharpProperty property = new CSharpProperty("Count", "int");

        property.setStatic(true);

        assertTrue(property.isStatic());
    }

    @Test
    void shouldAllowSettingPropertyVisibility() {
        CSharpProperty property = new CSharpProperty("Name", "string");

        property.setVisibility(CSharpVisibility.PUBLIC);

        assertEquals(CSharpVisibility.PUBLIC, property.getVisibility());
    }

    @Test
    void shouldAllowDifferentGetterAndSetterVisibility() {
        CSharpProperty property = new CSharpProperty("Name", "string");

        property.setHasGetter(true);
        property.setHasSetter(true);

        property.setGetterVisibility(CSharpVisibility.PUBLIC);
        property.setSetterVisibility(CSharpVisibility.PRIVATE);

        assertEquals(CSharpVisibility.PUBLIC, property.getGetterVisibility());
        assertEquals(CSharpVisibility.PRIVATE, property.getSetterVisibility());
    }

    @Test
    void shouldRejectNullVisibility() {
        CSharpProperty property = new CSharpProperty("Name", "string");

        assertThrows(NullPointerException.class, () ->
                property.setVisibility(null)
        );
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(NullPointerException.class, () ->
                new CSharpProperty(null, "string")
        );
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(NullPointerException.class, () ->
                new CSharpProperty("Name", null)
        );
    }
}
