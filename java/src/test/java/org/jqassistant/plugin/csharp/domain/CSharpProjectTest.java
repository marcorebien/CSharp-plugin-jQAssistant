package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpProjectTest {

    @Test
    void shouldCreateEmptyProject() {
        CSharpProject project = new CSharpProject();
        assertNotNull(project.getNamespaces());
        assertTrue(project.getNamespaces().isEmpty());
    }

    @Test
    void shouldAddNamespace() {
        CSharpProject project = new CSharpProject();
        CSharpNamespace ns = new CSharpNamespace("Test");

        project.addNamespace(ns);

        assertEquals(1, project.getNamespaces().size());
        assertSame(ns, project.getNamespaces().get(0));
    }

    @Test
    void shouldRejectNullNamespace() {
        CSharpProject project = new CSharpProject();
        assertThrows(NullPointerException.class, () -> project.addNamespace(null));
    }
}
