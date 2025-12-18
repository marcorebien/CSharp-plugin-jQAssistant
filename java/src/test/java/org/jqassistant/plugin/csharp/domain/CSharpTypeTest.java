package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpTypeTest {

    @Test
    void shouldExposeNameAndNamespace() {
        CSharpType type = new CSharpClass("MyClass", "My.Namespace");

        assertEquals("MyClass", type.getName());
        assertEquals("My.Namespace", type.getNamespace());
    }

    @Test
    void shouldBuildFullName() {
        CSharpType type = new CSharpClass("MyClass", "My.Namespace");

        assertEquals("My.Namespace.MyClass", type.getFullName());
    }

    @Test
    void shouldBuildFullNameWithoutNamespace() {
        CSharpType type = new CSharpClass("MyClass", "");

        assertEquals("MyClass", type.getFullName());
    }

    @Test
    void shouldDefaultToInternalVisibility() {
        CSharpType type = new CSharpClass("MyClass", "Test");

        assertEquals(CSharpVisibility.INTERNAL, type.getVisibility());
    }

    @Test
    void shouldSetVisibility() {
        CSharpType type = new CSharpClass("MyClass", "Test");

        type.setVisibility(CSharpVisibility.PUBLIC);

        assertEquals(CSharpVisibility.PUBLIC, type.getVisibility());
    }

    @Test
    void shouldRejectNullVisibility() {
        CSharpType type = new CSharpClass("MyClass", "Test");

        assertThrows(NullPointerException.class, () -> type.setVisibility(null));
    }

    @Test
    void shouldAddModifier() {
        CSharpType type = new CSharpClass("MyClass", "Test");

        type.addModifier(CSharpModifier.ABSTRACT);

        assertTrue(type.getModifiers().contains(CSharpModifier.ABSTRACT));
    }

    @Test
    void shouldAllowMultipleModifiers() {
        CSharpType type = new CSharpClass("MyClass", "Test");

        type.addModifier(CSharpModifier.ABSTRACT);
        type.addModifier(CSharpModifier.SEALED);

        assertEquals(2, type.getModifiers().size());
    }

    @Test
    void shouldRejectNullModifier() {
        CSharpType type = new CSharpClass("MyClass", "Test");

        assertThrows(NullPointerException.class, () -> type.addModifier(null));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(NullPointerException.class,
                () -> new CSharpClass(null, "Test"));
    }

    @Test
    void shouldRejectNullNamespace() {
        assertThrows(NullPointerException.class,
                () -> new CSharpClass("MyClass", null));
    }
}
