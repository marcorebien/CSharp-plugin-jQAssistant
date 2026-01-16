package org.jqassistant.plugin.csharp.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.plugin.csharp.api.descriptors.*;
import org.jqassistant.plugin.csharp.domain.*;
import org.jqassistant.plugin.csharp.api.model.CSharpVisibility;
import org.jqassistant.plugin.csharp.api.model.CSharpTypeKind;

import java.util.Objects;

public class CSharpDomainToDescriptorMapper {

    public void mapProject(CSharpProject project, ScannerContext ctx) {
        Objects.requireNonNull(project, "project must not be null");
        Objects.requireNonNull(ctx, "ctx must not be null");

        for (CSharpNamespace ns : project.getNamespaces()) {
            mapNamespace(ns, ctx);
        }
    }

    private CSharpNamespaceDescriptor mapNamespace(CSharpNamespace ns, ScannerContext ctx) {
        CSharpNamespaceDescriptor nsDesc = ctx.getStore()
                .create(CSharpNamespaceDescriptor.class);

        nsDesc.setName(ns.getName());

        for (CSharpType t : ns.getTypes()) {
            mapType(t, ctx);
            // Beziehung Namespace -> Type kommt, sobald du sie im Descriptor-Modell definiert hast
        }
        return nsDesc;
    }

    private CSharpTypeDescriptor mapType(CSharpType type, ScannerContext ctx) {
        return switch (type.getKind()) {
            case CLASS -> mapClass((CSharpClass) type, ctx);
            case INTERFACE -> mapInterface((CSharpInterface) type, ctx);
            case STRUCT -> mapStruct((CSharpStruct) type, ctx);
            case ENUM -> mapEnum((CSharpEnum) type, ctx);
            case RECORD -> mapRecord((CSharpRecord) type, ctx);
        };
    }

    private CSharpClassDescriptor mapClass(CSharpClass cls, ScannerContext ctx) {
        CSharpClassDescriptor d = ctx.getStore().create(CSharpClassDescriptor.class);
        mapCommonTypeFields(cls, d);

        d.setBaseClass(cls.getBaseClass());
        d.setInterfaces(cls.getInterfaces());

        // Members (Method/Field/Property) mappen wir als nächstes, sobald Descriptor-Interfaces existieren
        return d;
    }

    private CSharpInterfaceDescriptor mapInterface(CSharpInterface itf, ScannerContext ctx) {
        CSharpInterfaceDescriptor d = ctx.getStore().create(CSharpInterfaceDescriptor.class);
        mapCommonTypeFields(itf, d);

        // extended interfaces → wenn du es im InterfaceDescriptor abbilden willst, dann dort ergänzen
        return d;
    }

    private CSharpTypeDescriptor mapStruct(CSharpStruct st, ScannerContext ctx) {
        CSharpTypeDescriptor d = ctx.getStore().create(CSharpTypeDescriptor.class);
        mapCommonTypeFields(st, d);
        return d;
    }

    private CSharpTypeDescriptor mapEnum(CSharpEnum en, ScannerContext ctx) {
        CSharpTypeDescriptor d = ctx.getStore().create(CSharpTypeDescriptor.class);
        mapCommonTypeFields(en, d);
        return d;
    }

    private CSharpTypeDescriptor mapRecord(CSharpRecord rec, ScannerContext ctx) {
        CSharpTypeDescriptor d = ctx.getStore().create(CSharpTypeDescriptor.class);
        mapCommonTypeFields(rec, d);
        return d;
    }

    private static CSharpVisibility mapVisibility(org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility v) {
        return switch (v) {
            case PUBLIC -> CSharpVisibility.PUBLIC;
            case PRIVATE -> CSharpVisibility.PRIVATE;
            case PROTECTED -> CSharpVisibility.PROTECTED;
            case INTERNAL -> CSharpVisibility.INTERNAL;
            case PROTECTED_INTERNAL -> CSharpVisibility.PROTECTED_INTERNAL;
        };
    }

    private static CSharpTypeKind mapKind(org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind k) {
        return switch (k) {
            case CLASS -> CSharpTypeKind.CLASS;
            case INTERFACE -> CSharpTypeKind.INTERFACE;
            case STRUCT -> CSharpTypeKind.STRUCT;
            case ENUM -> CSharpTypeKind.ENUM;
            case RECORD -> CSharpTypeKind.RECORD;
        };
    }

    private void mapCommonTypeFields(CSharpType src, CSharpTypeDescriptor dst) {
        dst.setName(src.getName());
        dst.setNamespace(src.getNamespace());
        dst.setFullName(src.getFullName());
        dst.setVisibility(mapVisibility(src.getVisibility()));
        dst.setKind(mapKind(src.getKind()));
    }
}
