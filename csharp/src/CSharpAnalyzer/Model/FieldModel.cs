namespace CSharpAnalyzer.Model;

public sealed class FieldModel
{
    public string Name { get; init; } = null!;
    public string Type { get; init; } = null!;

    public CSharpVisibility Visibility { get; init; } = CSharpVisibility.Private;

    public bool IsStatic { get; init; }
}
