package org.jqassistant.plugin.csharp.impl.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.plugin.csharp.api.descriptors.*;
import org.jqassistant.plugin.csharp.domain.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CSharpDomainToDescriptorMapper {

    private final Map<String, CSharpTypeDescriptor> typeByFullName = new HashMap<>();

    public CSharpProjectDescriptor mapProject(CSharpProject project, ScannerContext ctx) {
        Objects.requireNonNull(project, "project must not be null");
        Objects.requireNonNull(ctx, "ctx must not be null");

        CSharpProjectDescriptor p = ctx.getStore().create(CSharpProjectDescriptor.class);
        p.setName("CSharpProject");

        for (CSharpNamespace ns : project.getNamespaces()) {
            CSharpNamespaceDescriptor nsDesc = mapNamespace(ns, ctx);
            p.getNamespaces().add(nsDesc); // CONTAINS_NAMESPACE
        }
        return p;
    }

    private CSharpNamespaceDescriptor mapNamespace(CSharpNamespace ns, ScannerContext ctx) {
        CSharpNamespaceDescriptor nsDesc = ctx.getStore().create(CSharpNamespaceDescriptor.class);
        nsDesc.setName(ns.getName());

        for (CSharpType t : ns.getTypes()) {
            CSharpTypeDescriptor tDesc = mapType(t, ctx);
            nsDesc.getTypes().add(tDesc); // DECLARES_TYPE
        }
        return nsDesc;
    }

    private CSharpTypeDescriptor mapType(CSharpType type, ScannerContext ctx) {
        String key = type.getFullName();
        CSharpTypeDescriptor existing = typeByFullName.get(key);
        if (existing != null) return existing;

        CSharpTypeDescriptor created = switch (type.getKind()) {
            case CLASS -> mapClass((CSharpClass) type, ctx);
            case INTERFACE -> mapInterface((CSharpInterface) type, ctx);
            case STRUCT -> mapStruct((CSharpStruct) type, ctx);
            case ENUM -> mapEnum((CSharpEnum) type, ctx);
            case RECORD -> mapRecord((CSharpRecord) type, ctx);
        };

        typeByFullName.put(key, created);
        return created;
    }

    // ---------------------------
    // Type Nodes (mit Kanten)
    // ---------------------------

    private CSharpClassDescriptor mapClass(CSharpClass cls, ScannerContext ctx) {
        CSharpClassDescriptor d = ctx.getStore().create(CSharpClassDescriptor.class);
        mapCommonTypeFields(cls, d);

        // EXTENDS (BaseType)
        if (cls.getBaseClass() != null && !cls.getBaseClass().isBlank()) {
            d.setBaseType(getOrCreateTypeRef(cls.getBaseClass(), ctx));
        }

        // IMPLEMENTS (Interfaces)
        for (String ifaceName : cls.getInterfaces()) {
            if (ifaceName == null || ifaceName.isBlank()) continue;
            d.getImplementedInterfaces().add(getOrCreateInterfaceRef(ifaceName, ctx));
        }

        // Members
        for (CSharpField f : cls.getFields()) d.getFields().add(mapField(f, ctx));
        for (CSharpProperty p : cls.getProperties()) d.getProperties().add(mapProperty(p, ctx));
        for (CSharpMethod m : cls.getMethods()) d.getMethods().add(mapMethod(m, ctx));

        return d;
    }

    private CSharpInterfaceDescriptor mapInterface(CSharpInterface itf, ScannerContext ctx) {
        CSharpInterfaceDescriptor d = ctx.getStore().create(CSharpInterfaceDescriptor.class);
        mapCommonTypeFields(itf, d);

        // Interface EXTENDS Interface
        for (String ifaceName : itf.getInterfaces()) {
            if (ifaceName == null || ifaceName.isBlank()) continue;
            d.getExtendedInterfaces().add(getOrCreateInterfaceRef(ifaceName, ctx)); // EXTENDS
        }

        for (CSharpMethod m : itf.getMethods()) d.getMethods().add(mapMethod(m, ctx));
        return d;
    }

    private CSharpTypeDescriptor mapStruct(CSharpStruct st, ScannerContext ctx) {
        CSharpStructDescriptor d = ctx.getStore().create(CSharpStructDescriptor.class);
        mapCommonTypeFields(st, d);

        for (CSharpField f : st.getFields()) d.getFields().add(mapField(f, ctx));
        for (CSharpProperty p : st.getProperties()) d.getProperties().add(mapProperty(p, ctx));
        for (CSharpMethod m : st.getMethods()) d.getMethods().add(mapMethod(m, ctx));

        return d;
    }

    private CSharpEnumDescriptor mapEnum(CSharpEnum en, ScannerContext ctx) {
        CSharpEnumDescriptor d = ctx.getStore().create(CSharpEnumDescriptor.class);
        mapCommonTypeFields(en, d);

        d.setMembers(en.getMembers()); // Property reicht hier
        return d;
    }

    private CSharpRecordDescriptor mapRecord(CSharpRecord rec, ScannerContext ctx) {
        CSharpRecordDescriptor d = ctx.getStore().create(CSharpRecordDescriptor.class);
        mapCommonTypeFields(rec, d);

        // EXTENDS
        if (rec.getBaseClass() != null && !rec.getBaseClass().isBlank()) {
            d.setBaseType(getOrCreateTypeRef(rec.getBaseClass(), ctx));
        }

        // IMPLEMENTS
        for (String ifaceName : rec.getInterfaces()) {
            if (ifaceName == null || ifaceName.isBlank()) continue;
            d.getImplementedInterfaces().add(getOrCreateInterfaceRef(ifaceName, ctx));
        }

        for (CSharpField f : rec.getFields()) d.getFields().add(mapField(f, ctx));
        for (CSharpProperty p : rec.getProperties()) d.getProperties().add(mapProperty(p, ctx));
        for (CSharpMethod m : rec.getMethods()) d.getMethods().add(mapMethod(m, ctx));

        return d;
    }

    // ---------------------------
    // Member Nodes (Method/Field/Property/Parameter)
    // ---------------------------

    private CSharpMethodDescriptor mapMethod(CSharpMethod m, ScannerContext ctx) {
        CSharpMethodDescriptor d = ctx.getStore().create(CSharpMethodDescriptor.class);

        d.setName(m.getName());
        d.setSignature(m.getSignature());
        d.setReturnType(m.getReturnType());
        d.setVisibility(m.getVisibility());

        // Flags aus EnumSet
        d.setStatic(m.getModifiers().contains(org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier.STATIC));
        d.setAbstract(m.getModifiers().contains(org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier.ABSTRACT));
        d.setVirtual(m.getModifiers().contains(org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier.VIRTUAL));
        d.setOverride(m.getModifiers().contains(org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier.OVERRIDE));
        d.setSealed(m.getModifiers().contains(org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier.SEALED));
        d.setAsync(m.getModifiers().contains(org.jqassistant.plugin.csharp.domain.enums.CSharpMethodModifier.ASYNC));

        for (CSharpParameter p : m.getParameters()) {
            d.getParameters().add(mapParameter(p, ctx)); // HAS_PARAMETER
        }
        return d;
    }

    private CSharpParameterDescriptor mapParameter(CSharpParameter p, ScannerContext ctx) {
        CSharpParameterDescriptor d = ctx.getStore().create(CSharpParameterDescriptor.class);
        d.setName(p.getName());
        d.setType(p.getType());
        d.setModifier(p.getModifier());
        d.setOptional(p.isOptional());
        d.setDefaultValue(p.getDefaultValue());
        return d;
    }

    private CSharpFieldDescriptor mapField(CSharpField f, ScannerContext ctx) {
        CSharpFieldDescriptor d = ctx.getStore().create(CSharpFieldDescriptor.class);
        d.setName(f.getName());
        d.setType(f.getType());
        d.setVisibility(f.getVisibility());
        d.setStatic(f.isStatic());
        return d;
    }

    private CSharpPropertyDescriptor mapProperty(CSharpProperty p, ScannerContext ctx) {
        CSharpPropertyDescriptor d = ctx.getStore().create(CSharpPropertyDescriptor.class);
        d.setName(p.getName());
        d.setType(p.getType());
        d.setHasGetter(p.hasGetter());
        d.setHasSetter(p.hasSetter());
        return d;
    }

    // ---------------------------
    // Helpers: Common fields + Referenzen
    // ---------------------------

    private void mapCommonTypeFields(CSharpType src, CSharpTypeDescriptor dst) {
        dst.setName(src.getName());
        dst.setNamespace(src.getNamespace());
        dst.setFullName(src.getFullName());
        dst.setKind(src.getKind());
        dst.setVisibility(src.getVisibility());

        // EnumSet<CSharpTypeModifier> -> "ABSTRACT,SEALED,STATIC"
        String mods = src.getModifiers().stream()
                .map(Enum::name)
                .sorted()
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        dst.setTypeModifiers(mods);
    }

    private CSharpTypeDescriptor getOrCreateTypeRef(String fullName, ScannerContext ctx) {
        CSharpTypeDescriptor existing = typeByFullName.get(fullName);
        if (existing != null) return existing;

        int lastDot = fullName.lastIndexOf('.');
        String ns = (lastDot > 0) ? fullName.substring(0, lastDot) : "";
        String name = (lastDot > 0) ? fullName.substring(lastDot + 1) : fullName;

        CSharpTypeDescriptor ref = ctx.getStore().create(CSharpTypeDescriptor.class);
        ref.setFullName(fullName);
        ref.setNamespace(ns);
        ref.setName(name);

        // Default-Referenz
        ref.setKind(org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind.CLASS);
        ref.setVisibility(org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility.INTERNAL);

        typeByFullName.put(fullName, ref);
        return ref;
    }

    private CSharpInterfaceDescriptor getOrCreateInterfaceRef(String fullName, ScannerContext ctx) {
        CSharpTypeDescriptor cached = typeByFullName.get(fullName);
        if (cached != null) {
            if (cached instanceof CSharpInterfaceDescriptor i) return i;
            throw new IllegalStateException("Expected interface for '" + fullName + "' but found " + cached.getClass().getSimpleName());
        }

        int lastDot = fullName.lastIndexOf('.');
        String ns = (lastDot > 0) ? fullName.substring(0, lastDot) : "";
        String name = (lastDot > 0) ? fullName.substring(lastDot + 1) : fullName;

        CSharpInterfaceDescriptor iface = ctx.getStore().create(CSharpInterfaceDescriptor.class);
        iface.setFullName(fullName);
        iface.setNamespace(ns);
        iface.setName(name);
        iface.setKind(org.jqassistant.plugin.csharp.domain.enums.CSharpTypeKind.INTERFACE);
        iface.setVisibility(org.jqassistant.plugin.csharp.domain.enums.CSharpVisibility.INTERNAL);

        typeByFullName.put(fullName, iface);
        return iface;
    }
}
