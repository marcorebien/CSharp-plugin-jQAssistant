package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonProjectImporterTest {

    private final JsonProjectImporter importer = new JsonProjectImporter();

    @Test
    void shouldImportClassFromJson() {
        InputStream json =
                getClass().getResourceAsStream("/json/valid-class.json");

        assertNotNull(json);

        CSharpProject project = importer.importProject(json);

        assertEquals(1, project.getNamespaces().size());

        CSharpNamespace ns = project.getNamespaces().get(0);
        assertEquals("MyApp.Core", ns.getName());
        assertEquals(1, ns.getTypes().size());

        CSharpType type = ns.getTypes().get(0);
        assertTrue(type instanceof CSharpClass);

        CSharpClass cls = (CSharpClass) type;

        assertEquals("UserService", cls.getName());
        assertEquals("MyApp.Core", cls.getNamespace());
        assertEquals("BaseService", cls.getBaseType());

        assertEquals(1, cls.getImplementedInterfaces().size());
        assertEquals("IDisposable", cls.getImplementedInterfaces().get(0));

        assertEquals(1, cls.getMethods().size());
        assertEquals("Execute", cls.getMethods().get(0).getName());

        assertEquals(1, cls.getFields().size());
        assertEquals("_repo", cls.getFields().get(0).getName());

        assertEquals(1, cls.getProperties().size());
        assertEquals("Name", cls.getProperties().get(0).getName());
    }

    @Test
    void shouldImportInterfaceFromJson() {
        InputStream json = getClass().getResourceAsStream("/json/valid-interface.json");
        assertNotNull(json);

        CSharpProject project = importer.importProject(json);

        assertEquals(1, project.getNamespaces().size());

        CSharpNamespace ns = project.getNamespaces().get(0);
        assertEquals(1, ns.getTypes().size());

        CSharpType type = ns.getTypes().get(0);
        assertInstanceOf(CSharpInterface.class, type);

        CSharpInterface iface = (CSharpInterface) type;

        assertEquals("IService", iface.getName());
        assertEquals("Contracts ", iface.getNamespace());

        // ✅ Interfaces = extended interfaces
        assertEquals(1, iface.getInterfaces().size());
        assertEquals("IDisposable", iface.getInterfaces().get(0));

        assertEquals(1, iface.getMethods().size());
        assertEquals("Run", iface.getMethods().get(0).getName());
    }


    @Test
    void shouldFailOnUnknownTypeKind() {
        InputStream json =
                getClass().getResourceAsStream("/json/invalid-unknown-kind.json");

        assertNotNull(json);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> importer.importProject(json)
        );

        assertNotNull(ex.getCause());
        assertTrue(ex.getCause().getMessage().contains("unknown"));
    }
}
