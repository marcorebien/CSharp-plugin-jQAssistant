package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpNamespaceTest {

    @Test
    void shouldCreateNamespaceWithName() {
        CSharpNamespace ns = new CSharpNamespace("MyApp.Services");

        assertEquals("MyApp.Services", ns.getName());
        assertTrue(ns.getTypes().isEmpty());
    }

    @Test
    void shouldAddTypes() {
        CSharpNamespace ns = new CSharpNamespace("MyApp");

        ns.addType(new CSharpClass("User"));
        ns.addType(new CSharpInterface("IUser"));

        assertEquals(2, ns.getTypes().size());
    }
}
