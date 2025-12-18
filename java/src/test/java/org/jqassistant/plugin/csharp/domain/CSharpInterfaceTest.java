package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpInterfaceTest {

    @Test
    void shouldCreateInterface() {
        CSharpInterface iface = new CSharpInterface("IMy", "Test");

        assertEquals("IMy", iface.getName());
        assertTrue(iface.getExtendedInterfaces().isEmpty());
    }

    @Test
    void shouldExtendInterface() {
        CSharpInterface iface = new CSharpInterface("IMy", "Test");

        iface.extendInterface("IDisposable");

        assertTrue(iface.getExtendedInterfaces().contains("IDisposable"));
    }

    @Test
    void shouldAddMethod() {
        CSharpInterface iface = new CSharpInterface("IMy", "Test");

        iface.addMethod(new CSharpMethod("Run", "void"));

        assertEquals(1, iface.getMethods().size());
    }
}
