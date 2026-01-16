package org.jqassistant.plugin.csharp.smoke;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.Store;
import org.jqassistant.plugin.csharp.api.descriptors.*;
import org.jqassistant.plugin.csharp.domain.CSharpProject;
import org.jqassistant.plugin.csharp.domain.JsonProjectImporter;
import org.jqassistant.plugin.csharp.impl.mapper.CSharpDomainToDescriptorMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSharpEndToEndSmokeTest {

    @Test
    void shouldRunEndToEndImportAndMap() throws Exception {
        System.out.println("=== C# -> JSON -> Java Import -> Map -> Descriptor Smoke ===");

        // 1) Import JSON -> Domain
        JsonProjectImporter importer = new JsonProjectImporter();
        CSharpProject project;

        try (InputStream in = getClass().getResourceAsStream("/importer/valid-full-project.json")) {
            assertNotNull(in, "Missing resource: /importer/valid-full-project.json");
            project = importer.importProject(in);
        }

        assertNotNull(project);
        assertFalse(project.getNamespaces().isEmpty(), "Project should contain namespaces");

        // 2) Mock ScannerContext + Store
        ScannerContext ctx = mock(ScannerContext.class);
        Store store = mock(Store.class);
        when(ctx.getStore()).thenReturn(store);

        // 3) store.create(...) -> Descriptor mocks mit "live" Lists
        // Project
        CSharpProjectDescriptor projectDesc = mock(CSharpProjectDescriptor.class);
        when(projectDesc.getNamespaces()).thenReturn(new ArrayList<>());
        when(store.create(CSharpProjectDescriptor.class)).thenReturn(projectDesc);

        // Namespace
        when(store.create(CSharpNamespaceDescriptor.class)).thenAnswer(inv -> {
            CSharpNamespaceDescriptor d = mock(CSharpNamespaceDescriptor.class);
            when(d.getTypes()).thenReturn(new ArrayList<>());
            return d;
        });

        // Type: Class
        when(store.create(CSharpClassDescriptor.class)).thenAnswer(inv -> {
            CSharpClassDescriptor d = mock(CSharpClassDescriptor.class);
            when(d.getMethods()).thenReturn(new ArrayList<>());
            when(d.getFields()).thenReturn(new ArrayList<>());
            when(d.getProperties()).thenReturn(new ArrayList<>());
            return d;
        });

        // Type: Interface
        when(store.create(CSharpInterfaceDescriptor.class)).thenAnswer(inv -> {
            CSharpInterfaceDescriptor d = mock(CSharpInterfaceDescriptor.class);
            when(d.getMethods()).thenReturn(new ArrayList<>());
            when(d.getFields()).thenReturn(new ArrayList<>());
            when(d.getProperties()).thenReturn(new ArrayList<>());
            return d;
        });

        // Type: Record
        when(store.create(CSharpRecordDescriptor.class)).thenAnswer(inv -> {
            CSharpRecordDescriptor d = mock(CSharpRecordDescriptor.class);
            when(d.getMethods()).thenReturn(new ArrayList<>());
            when(d.getFields()).thenReturn(new ArrayList<>());
            when(d.getProperties()).thenReturn(new ArrayList<>());
            return d;
        });

        // Type: Enum
        when(store.create(CSharpEnumDescriptor.class)).thenAnswer(inv -> {
            CSharpEnumDescriptor d = mock(CSharpEnumDescriptor.class);
            when(d.getMethods()).thenReturn(new ArrayList<>());
            when(d.getFields()).thenReturn(new ArrayList<>());
            when(d.getProperties()).thenReturn(new ArrayList<>());
            return d;
        });

        // Fallback: Base Type Descriptor (wird bei struct genutzt in deinem Mapper)
        when(store.create(CSharpTypeDescriptor.class)).thenAnswer(inv -> {
            CSharpTypeDescriptor d = mock(CSharpTypeDescriptor.class);
            when(d.getMethods()).thenReturn(new ArrayList<>());
            when(d.getFields()).thenReturn(new ArrayList<>());
            when(d.getProperties()).thenReturn(new ArrayList<>());
            return d;
        });

        // Members
        when(store.create(CSharpMethodDescriptor.class)).thenAnswer(inv -> {
            CSharpMethodDescriptor d = mock(CSharpMethodDescriptor.class);
            when(d.getParameters()).thenReturn(new ArrayList<>());
            return d;
        });

        when(store.create(CSharpParameterDescriptor.class)).thenReturn(mock(CSharpParameterDescriptor.class));
        when(store.create(CSharpFieldDescriptor.class)).thenReturn(mock(CSharpFieldDescriptor.class));
        when(store.create(CSharpPropertyDescriptor.class)).thenReturn(mock(CSharpPropertyDescriptor.class));

        // 4) Run Mapper
        CSharpDomainToDescriptorMapper mapper = new CSharpDomainToDescriptorMapper();
        CSharpProjectDescriptor out = mapper.mapProject(project, ctx);

        // 5) Smoke assertions: es wurde gemappt und Listen gefüllt
        assertNotNull(out);
        assertFalse(out.getNamespaces().isEmpty(), "ProjectDescriptor should have namespaces");

        // mindestens 1 Namespace-Node erstellt
        verify(store, atLeastOnce()).create(CSharpNamespaceDescriptor.class);

        // mindestens 1 Type-Node erstellt (je nach JSON)
        verify(store, atLeastOnce()).create(any(Class.class));

        System.out.println("=== Smoke OK: import + mapping executed ===");
    }
}
