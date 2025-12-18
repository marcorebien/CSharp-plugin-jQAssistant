package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpNamespaceTest {

    @Test
    void shouldCreateNamespace() {
        CSharpNamespace ns = new CSharpNamespace("Example.Core");
        assertEquals("Example.Core", ns.getName());
        assertEquals("Example.Core", ns.getFullName());
    }

    @Test
    void shouldAddType() {
        CSharpNamespace ns = new CSharpNamespace("Example");
        CSharpClass cls = new CSharpClass("MyClass", "Example");

        ns.addType(cls);

        assertEquals(1, ns.getTypes().size());
        assertSame(cls, ns.getTypes().get(0));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(NullPointerException.class, () -> new CSharpNamespace(null));
    }

    @Test
    void shouldRejectNullType() {
        CSharpNamespace ns = new CSharpNamespace("Example");
        assertThrows(NullPointerException.class, () -> ns.addType(null));
    }
}
