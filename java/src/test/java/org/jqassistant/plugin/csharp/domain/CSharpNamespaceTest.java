package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpNamespaceTest {

    @Test
    void shouldCreateNamespace() {
        CSharpNamespace ns = new CSharpNamespace("My.Namespace");

        assertEquals("My.Namespace", ns.getName());
        assertTrue(ns.getTypes().isEmpty());
    }

    @Test
    void shouldAddType() {
        CSharpNamespace ns = new CSharpNamespace("Test");
        CSharpClass cls = new CSharpClass("MyClass", "Test");

        ns.addType(cls);

        assertEquals(1, ns.getTypes().size());
        assertSame(cls, ns.getTypes().get(0));
    }

    @Test
    void shouldRejectNullType() {
        CSharpNamespace ns = new CSharpNamespace("Test");
        assertThrows(NullPointerException.class, () -> ns.addType(null));
    }
}
