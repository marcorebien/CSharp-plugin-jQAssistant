package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpClassTest {

    @Test
    void shouldCreateClass() {
        CSharpClass cls = new CSharpClass("MyClass", "Test");

        assertEquals("MyClass", cls.getName());
        assertEquals("Test", cls.getNamespace());
        assertNull(cls.getBaseType());
    }

    @Test
    void shouldSetBaseType() {
        CSharpClass cls = new CSharpClass("MyClass", "Test");
        cls.setBaseType("BaseClass");

        assertEquals("BaseClass", cls.getBaseType());
    }

    @Test
    void shouldImplementInterface() {
        CSharpClass cls = new CSharpClass("MyClass", "Test");

        cls.implementInterface("IDisposable");

        assertTrue(cls.getImplementedInterfaces().contains("IDisposable"));
    }

    @Test
    void shouldAddMembers() {
        CSharpClass cls = new CSharpClass("MyClass", "Test");

        cls.addMethod(new CSharpMethod("m", "void"));
        cls.addField(new CSharpField("x", "int"));
        cls.addProperty(new CSharpProperty("P", "string"));

        assertEquals(1, cls.getMethods().size());
        assertEquals(1, cls.getFields().size());
        assertEquals(1, cls.getProperties().size());
    }
}
