package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpInterfaceTest {

    @Test
    void shouldCreateInterfaceWithNameAndNamespace() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");

        assertEquals("ITest", iface.getName());
        assertEquals("Example", iface.getNamespace());
        assertEquals("Example.ITest", iface.getFullName());
    }

    @Test
    void shouldDefaultToNonStatic() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");
        assertFalse(iface.isStatic());
    }

    @Test
    void shouldSetStatic() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");

        iface.setStatic(true);

        assertTrue(iface.isStatic());
    }

    @Test
    void shouldAddExtendedInterface() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");

        iface.addInterface("IDisposable");

        assertEquals(1, iface.getInterfaces().size());
        assertEquals("IDisposable", iface.getInterfaces().get(0));
    }

    @Test
    void shouldAddMethod() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");
        CSharpMethod m = new CSharpMethod("Run", "void");

        iface.addMethod(m);

        assertEquals(1, iface.getMethods().size());
        assertSame(m, iface.getMethods().get(0));
    }

    @Test
    void shouldRejectNullInterfaceName() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");
        assertThrows(NullPointerException.class, () -> iface.addInterface(null));
    }

    @Test
    void shouldRejectNullMethod() {
        CSharpInterface iface = new CSharpInterface("ITest", "Example");
        assertThrows(NullPointerException.class, () -> iface.addMethod(null));
    }
}
