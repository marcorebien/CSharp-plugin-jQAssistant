package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpInterfaceTest {

    @Test
    void shouldCreateInterfaceWithName() {
        CSharpInterface i = new CSharpInterface("IService");

        assertEquals("IService", i.getName());
        assertNotNull(i.getMethods());
        assertNotNull(i.getInterfaces());
    }

    @Test
    void shouldAddMethodsAndInterfaces() {
        CSharpInterface i = new CSharpInterface("ILogger");

        i.addMethod(new CSharpMethod("Log", "void"));
        i.addInterface("IBase");

        assertEquals(1, i.getMethods().size());
        assertEquals(1, i.getInterfaces().size());
    }
}
