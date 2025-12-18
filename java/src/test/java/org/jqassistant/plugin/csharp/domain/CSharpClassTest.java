package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpClassTest {

    @Test
    void shouldCreateClassWithNameAndNamespace() {
        CSharpClass cls = new CSharpClass("MyClass", "My.Namespace");

        assertEquals("MyClass", cls.getName());
        assertEquals("My.Namespace", cls.getNamespace());
        assertEquals("My.Namespace.MyClass", cls.getFullName());
    }

    @Test
    void shouldHaveDefaultSemanticValues() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        assertFalse(cls.isAbstract());
        assertFalse(cls.isSealed());
        assertFalse(cls.isStatic());
        assertNull(cls.getBaseType());
    }

    @Test
    void shouldSetAbstractFlag() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        cls.setAbstract(true);

        assertTrue(cls.isAbstract());
    }

    @Test
    void shouldSetSealedFlag() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        cls.setSealed(true);

        assertTrue(cls.isSealed());
    }

    @Test
    void shouldSetStaticFlag() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        cls.setStatic(true);

        assertTrue(cls.isStatic());
    }

    @Test
    void shouldSetBaseType() {
        CSharpClass cls = new CSharpClass("Child", "Example");

        cls.setBaseType("System.Object");

        assertEquals("System.Object", cls.getBaseType());
    }

    @Test
    void shouldAddImplementedInterface() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        cls.implementInterface("IDisposable");

        assertEquals(1, cls.getImplementedInterfaces().size());
        assertEquals("IDisposable", cls.getImplementedInterfaces().get(0));
    }

    @Test
    void shouldAddMultipleInterfaces() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        cls.implementInterface("ICloneable");
        cls.implementInterface("IDisposable");

        assertEquals(2, cls.getImplementedInterfaces().size());
    }

    @Test
    void shouldAddMethod() {
        CSharpClass cls = new CSharpClass("Test", "Example");
        CSharpMethod method = new CSharpMethod("doWork", "void");

        cls.addMethod(method);

        assertEquals(1, cls.getMethods().size());
        assertSame(method, cls.getMethods().get(0));
    }

    @Test
    void shouldAddField() {
        CSharpClass cls = new CSharpClass("Test", "Example");
        CSharpField field = new CSharpField("count", "int");

        cls.addField(field);

        assertEquals(1, cls.getFields().size());
        assertSame(field, cls.getFields().get(0));
    }

    @Test
    void shouldAddProperty() {
        CSharpClass cls = new CSharpClass("Test", "Example");
        CSharpProperty property = new CSharpProperty("Name", "string");

        cls.addProperty(property);

        assertEquals(1, cls.getProperties().size());
        assertSame(property, cls.getProperties().get(0));
    }

    @Test
    void shouldRejectNullInterface() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        assertThrows(NullPointerException.class, () ->
                cls.implementInterface(null)
        );
    }

    @Test
    void shouldRejectNullMethod() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        assertThrows(NullPointerException.class, () ->
                cls.addMethod(null)
        );
    }

    @Test
    void shouldRejectNullField() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        assertThrows(NullPointerException.class, () ->
                cls.addField(null)
        );
    }

    @Test
    void shouldRejectNullProperty() {
        CSharpClass cls = new CSharpClass("Test", "Example");

        assertThrows(NullPointerException.class, () ->
                cls.addProperty(null)
        );
    }
}
