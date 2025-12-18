package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpProjectTest {

    @Test
    void shouldCreateEmptyProject() {
        CSharpProject project = new CSharpProject();

        assertNull(project.getName());
        assertNull(project.getRootPath());
        assertTrue(project.getNamespaces().isEmpty());
    }

    @Test
    void shouldSetProjectMetadata() {
        CSharpProject project = new CSharpProject();

        project.setName("MyProject");
        project.setRootPath("C:/repo/MyProject");

        assertEquals("MyProject", project.getName());
        assertEquals("C:/repo/MyProject", project.getRootPath());
    }

    @Test
    void shouldAddNamespace() {
        CSharpProject project = new CSharpProject();
        CSharpNamespace ns = new CSharpNamespace("Example");

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
