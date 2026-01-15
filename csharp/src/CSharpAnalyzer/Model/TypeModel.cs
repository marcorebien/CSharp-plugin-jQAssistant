namespace CSharpAnalyzer.Model;

public abstract class TypeModel
{
    public string Name { get; init; } = null!;
    public string Namespace { get; init; } = null!;

    public string FullName { get; init; } = null!;

    public TypeKind Kind { get; init; }

    public CSharpVisibility Visibility { get; init; } = CSharpVisibility.Internal;

    public TypeModifiers Modifiers { get; init; } = TypeModifiers.None;
}
