using Microsoft.CodeAnalysis;
using ModelTypeKind = CSharpAnalyzer.Model.TypeKind;

namespace CSharpAnalyzer.Analyzer;

internal static class TypeKindMapper
{
    public static ModelTypeKind FromRoslyn(INamedTypeSymbol symbol)
    {
        if (symbol.IsRecord)
            return ModelTypeKind.Record;

        return symbol.TypeKind switch
        {
            Microsoft.CodeAnalysis.TypeKind.Class => ModelTypeKind.Class,
            Microsoft.CodeAnalysis.TypeKind.Interface => ModelTypeKind.Interface,
            Microsoft.CodeAnalysis.TypeKind.Struct => ModelTypeKind.Struct,
            Microsoft.CodeAnalysis.TypeKind.Enum => ModelTypeKind.Enum,
            _ => ModelTypeKind.Class
        };
    }
}
