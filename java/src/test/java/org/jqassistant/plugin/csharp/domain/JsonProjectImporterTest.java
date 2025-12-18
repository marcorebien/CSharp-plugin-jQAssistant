package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.CSharpModifier;
import org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonProjectImporterTest {

    private final JsonProjectImporter importer = new JsonProjectImporter();

    @Test
    void shouldImportClassFromJson() {
        InputStream in = getClass()
                .getResourceAsStream("/valid-class.json");

        assertNotNull(in);

        CSharpProject project = importer.importProject(in);

        CSharpClass cls = (CSharpClass)
                project.getNamespaces().get(0)
                        .getTypes().get(0);

        assertEquals("MyClass", cls.getName());
        assertEquals("Demo", cls.getNamespace());
        assertEquals("BaseClass", cls.getBaseType());
        assertEquals(CSharpVisibility.PUBLIC, cls.getVisibility());
        assertTrue(cls.getModifiers().contains(CSharpModifier.ABSTRACT));
    }

    @Test
    void shouldFailOnUnknownTypeKind() {
        InputStream in = getClass()
                .getResourceAsStream("/invalid-type-kind.json");

        assertNotNull(in);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> importer.importProject(in)
        );

        assertTrue(ex.getMessage().contains("Unknown C# type kind"));
    }
}
