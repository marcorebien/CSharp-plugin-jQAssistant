using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.CodeAnalysis;
using CSharpAnalyzer.Model;
using ModelTypeKind = CSharpAnalyzer.Model.TypeKind;
using RoslynTypeKind = Microsoft.CodeAnalysis.TypeKind;

namespace CSharpAnalyzer.Analyzer;

public sealed class ProjectWalker
{
    private static readonly SymbolDisplayFormat TypeNameFormat = new(
        globalNamespaceStyle: SymbolDisplayGlobalNamespaceStyle.Omitted,
        typeQualificationStyle: SymbolDisplayTypeQualificationStyle.NameAndContainingTypesAndNamespaces,
        genericsOptions: SymbolDisplayGenericsOptions.IncludeTypeParameters,
        miscellaneousOptions:
            SymbolDisplayMiscellaneousOptions.EscapeKeywordIdentifiers |
            SymbolDisplayMiscellaneousOptions.UseSpecialTypes
    );

    private static readonly SymbolDisplayFormat NamespaceFormat = new(
        globalNamespaceStyle: SymbolDisplayGlobalNamespaceStyle.Omitted,
        typeQualificationStyle: SymbolDisplayTypeQualificationStyle.NameAndContainingTypesAndNamespaces
    );

    public ProjectModel BuildProjectModel(Compilation compilation)
    {
        if (compilation is null) throw new ArgumentNullException(nameof(compilation));

        var allTypes = AnalyzeCompilation(compilation);

        return new ProjectModel
        {
            Namespaces = allTypes
                .GroupBy(t => string.IsNullOrWhiteSpace(t.Namespace) ? "<global>" : t.Namespace)
                .OrderBy(g => g.Key, StringComparer.Ordinal)
                .Select(g => new NamespaceModel
                {
                    Name = g.Key,
                    Types = g.OrderBy(t => t.FullName, StringComparer.Ordinal).ToList()
                })
                .ToList()
        };
    }

    public IReadOnlyList<TypeModel> AnalyzeCompilation(Compilation compilation)
    {
        if (compilation is null) throw new ArgumentNullException(nameof(compilation));

        var result = new List<TypeModel>();
        var seen = new HashSet<string>(StringComparer.Ordinal);

        WalkNamespace(compilation.Assembly.GlobalNamespace, result, seen);

        return result;
    }

    private static void WalkNamespace(INamespaceSymbol ns, List<TypeModel> result, HashSet<string> seen)
    {
        foreach (var t in ns.GetTypeMembers())
            WalkType(t, result, seen);

        foreach (var child in ns.GetNamespaceMembers())
            WalkNamespace(child, result, seen);
    }

    private static void WalkType(INamedTypeSymbol type, List<TypeModel> result, HashSet<string> seen)
    {
        var fullName = type.ToDisplayString(TypeNameFormat);
        if (!seen.Add(fullName))
            return;

        var model = CreateTypeModel(type, fullName);
        result.Add(model);

        foreach (var nested in type.GetTypeMembers())
            WalkType(nested, result, seen);
    }

    private static TypeModel CreateTypeModel(INamedTypeSymbol symbol, string fullName)
    {
        var kind = TypeKindMapper.FromRoslyn(symbol);

        var ns = symbol.ContainingNamespace?.ToDisplayString(NamespaceFormat) ?? "";

        var visibility = MapVisibility(symbol.DeclaredAccessibility);
        var typeMods = MapTypeModifiers(symbol);

        // Members vorberechnen (für init-only)
        if (kind == ModelTypeKind.Interface)
        {
            var interfaces = symbol.Interfaces.Select(i => i.ToDisplayString(TypeNameFormat)).ToList();
            var methods = symbol.GetMembers().OfType<IMethodSymbol>()
                .Where(IsRelevantMethod)
                .Select(MapMethod)
                .ToList();

            return new InterfaceModel
            {
                Name = symbol.Name,
                Namespace = ns,
                FullName = fullName,
                Visibility = visibility,
                Modifiers = typeMods,
                Interfaces = interfaces,
                Methods = methods
            };
        }

        // Default: ClassModel (auch für Struct/Enum/Record – Kind unterscheidet)
        var baseClass = GetBaseClass(symbol);
        var impl = symbol.Interfaces.Select(i => i.ToDisplayString(TypeNameFormat)).ToList();

        var fields = symbol.GetMembers().OfType<IFieldSymbol>()
            .Where(IsRelevantField)
            .Select(MapField)
            .ToList();

        var props = symbol.GetMembers().OfType<IPropertySymbol>()
            .Where(p => !p.IsImplicitlyDeclared)
            .Select(MapProperty)
            .ToList();

        var methods2 = symbol.GetMembers().OfType<IMethodSymbol>()
            .Where(IsRelevantMethod)
            .Select(MapMethod)
            .ToList();

        // Enum-Members zusätzlich sichtbar machen (als Fields)
        if (kind == ModelTypeKind.Enum)
        {
            var enumMembers = symbol.GetMembers()
                .OfType<IFieldSymbol>()
                .Where(f => f.HasConstantValue && !f.IsImplicitlyDeclared)
                .Select(f => new FieldModel
                {
                    Name = f.Name,
                    Type = symbol.ToDisplayString(TypeNameFormat),
                    Visibility = CSharpVisibility.Public,
                    IsStatic = true
                });

            var existing = new HashSet<string>(fields.Select(f => f.Name), StringComparer.Ordinal);
            foreach (var em in enumMembers)
                if (existing.Add(em.Name)) fields.Add(em);
        }

        return new ClassModel
        {
            Name = symbol.Name,
            Namespace = ns,
            FullName = fullName,
            Kind = kind,
            Visibility = visibility,
            Modifiers = typeMods,
            BaseClass = baseClass,
            Interfaces = impl,
            Fields = fields,
            Properties = props,
            Methods = methods2
        };
    }

    private static string? GetBaseClass(INamedTypeSymbol symbol)
    {
        var baseType = symbol.BaseType;
        if (baseType is null) return null;
        if (baseType.SpecialType == SpecialType.System_Object) return null;
        return baseType.ToDisplayString(TypeNameFormat);
    }

    // ---------------- Mapping ----------------

    private static bool IsRelevantField(IFieldSymbol f)
    {
        if (f.IsImplicitlyDeclared) return false;
        if (f.AssociatedSymbol is IPropertySymbol) return false;
        return true;
    }

    private static FieldModel MapField(IFieldSymbol f) => new()
    {
        Name = f.Name,
        Type = f.Type.ToDisplayString(TypeNameFormat),
        Visibility = MapVisibility(f.DeclaredAccessibility),
        IsStatic = f.IsStatic
    };

    private static PropertyModel MapProperty(IPropertySymbol p) => new()
    {
        Name = p.Name,
        Type = p.Type.ToDisplayString(TypeNameFormat),
        HasGetter = p.GetMethod is not null,
        HasSetter = p.SetMethod is not null
    };

    private static bool IsRelevantMethod(IMethodSymbol m)
    {
        if (m.IsImplicitlyDeclared) return false;
        if (m.MethodKind is MethodKind.PropertyGet or MethodKind.PropertySet) return false;
        if (m.MethodKind is MethodKind.EventAdd or MethodKind.EventRemove) return false;

        return m.MethodKind is MethodKind.Ordinary or MethodKind.Constructor or MethodKind.StaticConstructor;
    }

    private static MethodModel MapMethod(IMethodSymbol m)
    {
        var parameters = m.Parameters.Select(MapParameter).ToList();
        var sig = BuildSignature(m, parameters);

        return new MethodModel
        {
            Name = m.Name,
            Signature = sig,
            ReturnType = m.ReturnType.ToDisplayString(TypeNameFormat),
            Visibility = MapVisibility(m.DeclaredAccessibility),
            Modifiers = MapMethodModifiers(m),
            Parameters = parameters
        };
    }

    private static ParameterModel MapParameter(IParameterSymbol p) => new()
    {
        Name = p.Name,
        Type = p.Type.ToDisplayString(TypeNameFormat),
        Modifier = MapParameterModifier(p),
        Optional = p.IsOptional,
        DefaultValue = p.HasExplicitDefaultValue ? (p.ExplicitDefaultValue?.ToString()) : null
    };

    private static string BuildSignature(IMethodSymbol m, List<ParameterModel> parameters)
    {
        var paramSig = string.Join(", ", parameters.Select(p =>
        {
            var mod = p.Modifier switch
            {
                CSharpParameterModifier.Ref => "ref ",
                CSharpParameterModifier.Out => "out ",
                CSharpParameterModifier.In => "in ",
                CSharpParameterModifier.Params => "params ",
                _ => ""
            };

            return $"{mod}{p.Type} {p.Name}";
        }));

        var ret = m.ReturnType.ToDisplayString(TypeNameFormat);
        return $"{m.Name}({paramSig}): {ret}";
    }

    private static CSharpVisibility MapVisibility(Accessibility a) => a switch
    {
        Accessibility.Public => CSharpVisibility.Public,
        Accessibility.Private => CSharpVisibility.Private,
        Accessibility.Protected => CSharpVisibility.Protected,
        Accessibility.Internal => CSharpVisibility.Internal,
        Accessibility.ProtectedAndInternal => CSharpVisibility.ProtectedInternal,
        Accessibility.ProtectedOrInternal => CSharpVisibility.ProtectedInternal,
        _ => CSharpVisibility.Internal
    };

    private static TypeModifiers MapTypeModifiers(INamedTypeSymbol s)
    {
        var mods = TypeModifiers.None;

        if (s.IsStatic) mods |= TypeModifiers.Static;

        if (s.TypeKind == RoslynTypeKind.Class)
        {
            if (s.IsAbstract) mods |= TypeModifiers.Abstract;
            if (s.IsSealed) mods |= TypeModifiers.Sealed;
        }

        return mods;
    }

    private static MethodModifiers MapMethodModifiers(IMethodSymbol m)
    {
        var mods = MethodModifiers.None;

        if (m.IsStatic) mods |= MethodModifiers.Static;
        if (m.IsAbstract) mods |= MethodModifiers.Abstract;
        if (m.IsVirtual) mods |= MethodModifiers.Virtual;
        if (m.IsOverride) mods |= MethodModifiers.Override;
        if (m.IsSealed) mods |= MethodModifiers.Sealed;
        if (m.IsAsync) mods |= MethodModifiers.Async;

        return mods;
    }

    private static CSharpParameterModifier MapParameterModifier(IParameterSymbol p)
    {
        if (p.IsParams) return CSharpParameterModifier.Params;

        return p.RefKind switch
        {
            RefKind.Ref => CSharpParameterModifier.Ref,
            RefKind.Out => CSharpParameterModifier.Out,
            RefKind.In => CSharpParameterModifier.In,
            _ => CSharpParameterModifier.None
        };
    }
}
