package org.jqassistant.plugin.csharp.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.plugin.csharp.api.descriptors.*;
import org.jqassistant.plugin.csharp.domain.*;
import org.jqassistant.plugin.csharp.domain.enums.*;
import org.jqassistant.plugin.csharp.mapper.TestScannerContext;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class CSharpDomainToDescriptorMapperTest {

    @Test
    void shouldMapNamespacesAndTypesAndMembersAsGraphEdges() {
        // Domain model build
        CSharpProject project = new CSharpProject();

        CSharpNamespace ns = new CSharpNamespace("SampleApp");
        project.addNamespace(ns);

        CSharpClass program = new CSharpClass("Program", "SampleApp", "SampleApp.Program");
        program.setVisibility(CSharpVisibility.INTERNAL);
        program.setModifiers(EnumSet.of(CSharpTypeModifier.STATIC, CSharpTypeModifier.SEALED));
        program.addInterface("System.IDisposable");

        // members
        CSharpField f = new CSharpField("_count", "int");
        f.setVisibility(CSharpVisibility.PRIVATE);
        f.setStatic(false);
        program.addField(f);

        CSharpProperty p = new CSharpProperty("Name", "string");
        p.setHasGetter(true);
        p.setHasSetter(false);
        program.addProperty(p);

        CSharpMethod m = new CSharpMethod("Main", "Main(string[] args): void", "void");
        m.setVisibility(CSharpVisibility.PRIVATE);
        m.setModifiers(EnumSet.of(CSharpMethodModifier.STATIC));

        CSharpParameter arg = new CSharpParameter("args", "string[]");
        arg.setModifier(CSharpParameterModifier.NONE);
        arg.setOptional(false);
        m.addParameter(arg);

        program.addMethod(m);

        ns.addType(program);

        // Map
        ScannerContext ctx = TestScannerContext.create();
        CSharpDomainToDescriptorMapper mapper = new CSharpDomainToDescriptorMapper();
        CSharpProjectDescriptor pDesc = mapper.mapProject(project, ctx);

        // Assertions: Project -> Namespace edge
        assertEquals(1, pDesc.getNamespaces().size());
        CSharpNamespaceDescriptor nsDesc = pDesc.getNamespaces().get(0);
        assertEquals("SampleApp", nsDesc.getName());

        // Assertions: Namespace -> Type edge
        assertEquals(1, nsDesc.getTypes().size());
        CSharpTypeDescriptor t0 = nsDesc.getTypes().get(0);
        assertEquals("Program", t0.getName());
        assertEquals("SampleApp", t0.getNamespace());
        assertEquals("SampleApp.Program", t0.getFullName());
        assertEquals(CSharpTypeKind.CLASS, t0.getKind());
        assertEquals(CSharpVisibility.INTERNAL, t0.getVisibility());

        // Member relations are edges (DECLARES_*)
        assertEquals(1, t0.getFields().size());
        assertEquals("_count", t0.getFields().get(0).getName());

        assertEquals(1, t0.getProperties().size());
        assertEquals("Name", t0.getProperties().get(0).getName());
        assertTrue(t0.getProperties().get(0).hasGetter());
        assertFalse(t0.getProperties().get(0).hasSetter());

        assertEquals(1, t0.getMethods().size());
        assertEquals("Main", t0.getMethods().get(0).getName());
        assertEquals(1, t0.getMethods().get(0).getParameters().size());
        assertEquals("args", t0.getMethods().get(0).getParameters().get(0).getName());

        // typeModifiers flattened property
        assertEquals("SEALED,STATIC", t0.getTypeModifiers()); // sorted in mapper
    }

    @Test
    void shouldCreateAndReuseInterfaceReferenceNodesForImplements() {
        CSharpProject project = new CSharpProject();
        CSharpNamespace ns = new CSharpNamespace("SampleApp");
        project.addNamespace(ns);

        CSharpClass a = new CSharpClass("A", "SampleApp", "SampleApp.A");
        a.addInterface("System.IDisposable");
        ns.addType(a);

        CSharpClass b = new CSharpClass("B", "SampleApp", "SampleApp.B");
        b.addInterface("System.IDisposable");
        ns.addType(b);

        ScannerContext ctx = TestScannerContext.create();
        CSharpDomainToDescriptorMapper mapper = new CSharpDomainToDescriptorMapper();
        CSharpProjectDescriptor pDesc = mapper.mapProject(project, ctx);

        CSharpTypeDescriptor aDesc = pDesc.getNamespaces().get(0).getTypes().get(0);
        CSharpTypeDescriptor bDesc = pDesc.getNamespaces().get(0).getTypes().get(1);

        assertTrue(aDesc instanceof CSharpClassDescriptor);
        assertTrue(bDesc instanceof CSharpClassDescriptor);

        CSharpInterfaceDescriptor ia = ((CSharpClassDescriptor) aDesc).getImplementedInterfaces().get(0);
        CSharpInterfaceDescriptor ib = ((CSharpClassDescriptor) bDesc).getImplementedInterfaces().get(0);

        // Very important: same node reused (cache)
        assertSame(ia, ib, "Expected the same interface reference node to be reused via cache");
        assertEquals("System.IDisposable", ia.getFullName());
        assertEquals(CSharpTypeKind.INTERFACE, ia.getKind());
    }

    @Test
    void shouldMapExtendsEdgeForBaseType() {
        CSharpProject project = new CSharpProject();
        CSharpNamespace ns = new CSharpNamespace("SampleApp");
        project.addNamespace(ns);

        CSharpClass child = new CSharpClass("Child", "SampleApp", "SampleApp.Child");
        child.setBaseClass("SampleApp.BaseClass");
        ns.addType(child);

        ScannerContext ctx = TestScannerContext.create();
        CSharpDomainToDescriptorMapper mapper = new CSharpDomainToDescriptorMapper();
        CSharpProjectDescriptor pDesc = mapper.mapProject(project, ctx);

        CSharpTypeDescriptor childDesc = pDesc.getNamespaces().get(0).getTypes().get(0);
        assertTrue(childDesc instanceof CSharpClassDescriptor);

        CSharpTypeDescriptor base = ((CSharpClassDescriptor) childDesc).getBaseType();
        assertNotNull(base);
        assertEquals("SampleApp.BaseClass", base.getFullName());
        assertEquals("SampleApp", base.getNamespace());
        assertEquals("BaseClass", base.getName());
    }
}
