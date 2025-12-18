package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpFieldTest {

    @Test
    void shouldCreateFieldWithNameAndType() {
        CSharpField field = new CSharpField("count", "int");

        assertEquals("count", field.getName());
        assertEquals("int", field.getType());
    }

    @Test
    void shouldDefaultToPrivateVisibility() {
        CSharpField field = new CSharpField("count", "int");

        assertEquals(CSharpVisibility.PRIVATE, field.getVisibility());
    }

    @Test
    void shouldAllowChangingVisibility() {
        CSharpField field = new CSharpField("count", "int");

        field.setVisibility(CSharpVisibility.PUBLIC);

        assertEquals(CSharpVisibility.PUBLIC, field.getVisibility());
    }

    @Test
    void shouldAllowStaticField() {
        CSharpField field = new CSharpField("count", "int");

        field.setStatic(true);

        assertTrue(field.isStatic());
    }

    @Test
    void shouldAllowReadonlyField() {
        CSharpField field = new CSharpField("id", "Guid");

        field.setReadonly(true);

        assertTrue(field.isReadonly());
    }

    @Test
    void shouldAllowConstField() {
        CSharpField field = new CSharpField("MAX", "int");

        field.setConst(true);

        assertTrue(field.isConst());
    }

    @Test
    void shouldAllowCombinedModifiers() {
        CSharpField field = new CSharpField("VERSION", "string");

        field.setStatic(true);
        field.setConst(true);
        field.setVisibility(CSharpVisibility.PUBLIC);

        assertTrue(field.isStatic());
        assertTrue(field.isConst());
        assertEquals(CSharpVisibility.PUBLIC, field.getVisibility());
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(NullPointerException.class, () ->
                new CSharpField(null, "int")
        );
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(NullPointerException.class, () ->
                new CSharpField("x", null)
        );
    }

    @Test
    void shouldRejectNullVisibility() {
        CSharpField field = new CSharpField("x", "int");

        assertThrows(NullPointerException.class, () ->
                field.setVisibility(null)
        );
    }
}
