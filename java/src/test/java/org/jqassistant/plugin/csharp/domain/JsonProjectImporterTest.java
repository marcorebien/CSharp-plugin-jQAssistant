package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonProjectImporterTest {

    private final JsonProjectImporter importer = new JsonProjectImporter();

    @Test
    void shouldImportSimpleCSharpProjectFromJsonResource() {
        InputStream jsonStream = getClass()
                .getClassLoader()
                .getResourceAsStream("json/valid-simple-class.json");

        assertNotNull(jsonStream, "Test JSON resource not found");

        CSharpProject project = importer.importProject(jsonStream);

        // Project
        assertNotNull(project);
        assertEquals(1, project.getNamespaces().size());

        // Namespace
        CSharpNamespace namespace = project.getNamespaces().get(0);
        assertEquals("Example.Namespace", namespace.getName());
        assertEquals(1, namespace.getTypes().size());

        // Type
        assertTrue(namespace.getTypes().get(0) instanceof CSharpClass);
        CSharpClass cls = (CSharpClass) namespace.getTypes().get(0);

        assertEquals("MyClass", cls.getName());
        assertEquals("Example.Namespace", cls.getNamespace());
        assertEquals("BaseClass", cls.getBaseClass());

        // Modifiers
        assertEquals(1, cls.getModifiers().size());
        assertTrue(cls.getModifiers().contains("public"));

        // Interfaces
        assertEquals(1, cls.getInterfaces().size());
        assertEquals("IMyInterface", cls.getInterfaces().get(0));

        // Fields
        assertEquals(1, cls.getFields().size());
        assertEquals("field1", cls.getFields().get(0).getName());
        assertEquals("int", cls.getFields().get(0).getType());

        // Properties
        assertEquals(1, cls.getProperties().size());
        assertEquals("Property1", cls.getProperties().get(0).getName());
        assertEquals("string", cls.getProperties().get(0).getType());

        // Methods
        assertEquals(1, cls.getMethods().size());
        CSharpMethod method = cls.getMethods().get(0);

        assertEquals("DoSomething", method.getName());
        assertEquals("void", method.getReturnType());

        // Parameters
        assertEquals(1, method.getParameters().size());
        CSharpParameter param = method.getParameters().get(0);

        assertEquals("value", param.getName());
        assertEquals("int", param.getType());
    }

    @Test
    void shouldFailOnUnknownTypeKind() {
        InputStream json =
                getClass().getResourceAsStream("/json/invalid-unknown-kind.json");

        assertNotNull(json);

        assertThrows(
                IllegalStateException.class,
                () -> importer.importProject(json)
        );
    }

    @Test
    void shouldImportInterfaceFromJsonResource() {
        InputStream json =
                getClass().getResourceAsStream("/json/valid-interface.json");

        assertNotNull(json);

        CSharpProject project = importer.importProject(json);

        assertEquals(1, project.getNamespaces().size());

        CSharpNamespace ns = project.getNamespaces().get(0);
        assertEquals("MyApp.Services", ns.getName());
        assertEquals(1, ns.getTypes().size());

        assertTrue(ns.getTypes().get(0) instanceof CSharpInterface);

        CSharpInterface iface = (CSharpInterface) ns.getTypes().get(0);
        assertEquals("IMyService", iface.getName());
        assertEquals("MyApp.Services", iface.getNamespace());

        assertEquals(1, iface.getInterfaces().size());
        assertEquals("IDisposable", iface.getInterfaces().get(0));

        assertEquals(1, iface.getMethods().size());

        CSharpMethod method = iface.getMethods().get(0);
        assertEquals("Execute", method.getName());
        assertEquals("void", method.getReturnType());

        assertEquals(1, method.getParameters().size());
        assertEquals("input", method.getParameters().get(0).getName());
        assertEquals("string", method.getParameters().get(0).getType());
    }




}
