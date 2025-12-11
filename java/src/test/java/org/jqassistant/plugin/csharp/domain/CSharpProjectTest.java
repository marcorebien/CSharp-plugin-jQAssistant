package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpProjectTest {

    @Test
    void shouldInitializeWithEmptyNamespaceList() {
        CSharpProject project = new CSharpProject();

        assertNotNull(project.getNamespaces());
        assertTrue(project.getNamespaces().isEmpty());
    }

    @Test
    void shouldAddNamespaces() {
        CSharpProject project = new CSharpProject();

        project.addNamespace(new CSharpNamespace("MyApp.Services"));

        assertEquals(1, project.getNamespaces().size());
    }
}
