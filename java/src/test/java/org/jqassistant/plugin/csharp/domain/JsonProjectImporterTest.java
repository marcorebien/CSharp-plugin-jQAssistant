package org.jqassistant.plugin.csharp.domain;

import org.jqassistant.plugin.csharp.domain.enums.*;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class JsonProjectImporterTest {

    private final JsonProjectImporter importer = new JsonProjectImporter();

    @Test
    void shouldImportFullProjectFromJson() {
        CSharpProject project = importResource("/importer/valid-full-project.json");

        assertNotNull(project);
        assertEquals(3, project.getNamespaces().size(), "Expected 3 namespaces");

        // --- Namespace 1: SampleApp ---
        CSharpNamespace ns1 = project.getNamespaces().stream()
                .filter(n -> n.getName().equals("SampleApp"))
                .findFirst()
                .orElseThrow();

        assertEquals(1, ns1.getTypes().size());
        assertEquals("SampleApp", ns1.getName());

        CSharpType t0 = ns1.getTypes().get(0);
        assertEquals(CSharpTypeKind.CLASS, t0.getKind());
        assertEquals("Program", t0.getName());
        assertEquals("SampleApp", t0.getNamespace());
        assertEquals("SampleApp.Program", t0.getFullName());
        assertEquals(CSharpVisibility.INTERNAL, t0.getVisibility());

        CSharpClass program = (CSharpClass) t0;
        // modifiers (TypeModifiers)
        assertTrue(program.getModifiers().contains(CSharpTypeModifier.STATIC));
        assertTrue(program.getModifiers().contains(CSharpTypeModifier.SEALED));

        // base + interfaces
        assertNull(program.getBaseClass(), "BaseClass expected null for Program");
        assertTrue(program.getInterfaces().contains("System.IDisposable"));

        // fields
        assertEquals(2, program.getFields().size());
        CSharpField f0 = program.getFields().stream().filter(f -> f.getName().equals("_count")).findFirst().orElseThrow();
        assertEquals("int", f0.getType());
        assertEquals(CSharpVisibility.PRIVATE, f0.getVisibility());
        assertFalse(f0.isStatic());


        CSharpField f1 = program.getFields().stream().filter(f -> f.getName().equals("Global")).findFirst().orElseThrow();
        assertEquals(CSharpVisibility.PUBLIC, f1.getVisibility());
        assertTrue(f1.isStatic());

        // properties
        assertEquals(2, program.getProperties().size());
        CSharpProperty pName = program.getProperties().stream().filter(p -> p.getName().equals("Name")).findFirst().orElseThrow();
        assertEquals("string", pName.getType());
        assertTrue(pName.hasGetter());
        assertFalse(pName.hasSetter());

        CSharpProperty pAge = program.getProperties().stream().filter(p -> p.getName().equals("Age")).findFirst().orElseThrow();
        assertTrue(pAge.hasGetter());
        assertTrue(pAge.hasSetter());

        // methods + parameters
        assertEquals(2, program.getMethods().size());

        CSharpMethod main = program.getMethods().stream().filter(m -> m.getName().equals("Main")).findFirst().orElseThrow();
        assertEquals("void", main.getReturnType());
        assertEquals(CSharpVisibility.PRIVATE, main.getVisibility());
        assertTrue(main.getModifiers().contains(CSharpMethodModifier.STATIC));
        assertEquals(1, main.getParameters().size());

        CSharpParameter args = main.getParameters().get(0);
        assertEquals("args", args.getName());
        assertEquals("string[]", args.getType());
        assertEquals(CSharpParameterModifier.NONE, args.getModifier());
        assertFalse(args.isOptional());
        assertNull(args.getDefaultValue());

        CSharpMethod compute = program.getMethods().stream().filter(m -> m.getName().equals("Compute")).findFirst().orElseThrow();
        assertEquals("int", compute.getReturnType());
        assertEquals(CSharpVisibility.PUBLIC, compute.getVisibility());
        assertTrue(compute.getModifiers().contains(CSharpMethodModifier.VIRTUAL));
        assertTrue(compute.getModifiers().contains(CSharpMethodModifier.ASYNC));
        assertEquals(2, compute.getParameters().size());

        CSharpParameter x = compute.getParameters().get(0);
        assertEquals(CSharpParameterModifier.REF, x.getModifier());
        assertFalse(x.isOptional());

        CSharpParameter y = compute.getParameters().get(1);
        assertEquals(CSharpParameterModifier.NONE, y.getModifier());
        assertTrue(y.isOptional());
        assertEquals("42", y.getDefaultValue());

        // --- Namespace 2: SampleApp.Models ---
        CSharpNamespace ns2 = project.getNamespaces().stream()
                .filter(n -> n.getName().equals("SampleApp.Models"))
                .findFirst()
                .orElseThrow();

        assertEquals(4, ns2.getTypes().size(), "Expected interface, struct, enum, record");

        // interface
        CSharpInterface iService = (CSharpInterface) ns2.getTypes().stream()
                .filter(t -> t.getKind() == CSharpTypeKind.INTERFACE)
                .findFirst().orElseThrow();

        assertEquals(CSharpVisibility.PUBLIC, iService.getVisibility());
        assertTrue(iService.getInterfaces().contains("System.IAsyncDisposable"));
        assertEquals(1, iService.getMethods().size());
        assertEquals("Run", iService.getMethods().get(0).getName());

        // struct
        CSharpStruct point = (CSharpStruct) ns2.getTypes().stream()
                .filter(t -> t.getKind() == CSharpTypeKind.STRUCT)
                .findFirst().orElseThrow();

        assertEquals(2, point.getFields().size());

        // enum
        CSharpEnum status = (CSharpEnum) ns2.getTypes().stream()
                .filter(t -> t.getKind() == CSharpTypeKind.ENUM)
                .findFirst().orElseThrow();

        assertEquals(3, status.getMembers().size());
        assertTrue(status.getMembers().contains("Active"));

        // record
        CSharpRecord user = (CSharpRecord) ns2.getTypes().stream()
                .filter(t -> t.getKind() == CSharpTypeKind.RECORD)
                .findFirst().orElseThrow();

        assertEquals(CSharpVisibility.PUBLIC, user.getVisibility());
        assertTrue(user.getInterfaces().stream().anyMatch(s -> s.contains("IEquatable")));
        assertEquals(1, user.getProperties().size());
        assertEquals("Id", user.getProperties().get(0).getName());
    }

    @Test
    void shouldFailOnUnknownTypeKindWithIllegalArgument() {
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> importResource("/importer/invalid-unknown-type-kind.json")
        );

        assertNotNull(ex.getCause(), "Expected Jackson cause");
        String causeMsg = ex.getCause().getMessage().toLowerCase();

        assertTrue(
                causeMsg.contains("type id") ||
                        causeMsg.contains("could not resolve type id") ||
                        causeMsg.contains("unknown") ||
                        causeMsg.contains("kind"),
                "Cause message should mention unknown type id / kind, but was: " + ex.getCause().getMessage()
        );
    }

    @Test
    void shouldWrapInvalidJsonAsIllegalState() {
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> importResource("/importer/invalid-json-syntax.json")
        );
        assertTrue(ex.getMessage().contains("Failed to import"),
                "Expected wrapper message from importer");
        assertNotNull(ex.getCause(), "Expected original cause from Jackson");
    }

    @Test
    void shouldHandleEmptyNamespaces() {
        CSharpProject project = importResource("/importer/empty-namespaces.json");
        assertNotNull(project);
        assertNotNull(project.getNamespaces());
        assertEquals(0, project.getNamespaces().size());
    }

    // ---------------- helpers ----------------

    private CSharpProject importResource(String name) {
        String normalized = name.startsWith("/") ? name.substring(1) : name;

        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(normalized)) {

            assertNotNull(in, "Missing test resource: " + name);
            return importer.importProject(in);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Test failed reading resource: " + name, e);
        }
    }

}
