namespace CSharpAnalyzer.Model;

public sealed class MethodModel
{
    public string Name { get; init; } = null!;

    public string Signature { get; init; } = null!;

    public string ReturnType { get; init; } = null!;

    public CSharpVisibility Visibility { get; init; } = CSharpVisibility.Private;

    public MethodModifiers Modifiers { get; init; } = MethodModifiers.None;

    public List<ParameterModel> Parameters { get; init; } = new();
}
