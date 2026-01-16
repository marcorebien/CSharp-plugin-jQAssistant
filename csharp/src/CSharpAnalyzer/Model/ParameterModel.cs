namespace CSharpAnalyzer.Model;

public sealed class ParameterModel
{
    public string Name { get; init; } = null!;
    public string Type { get; init; } = null!;

    public CSharpParameterModifier Modifier { get; init; } = CSharpParameterModifier.None;

    public bool Optional { get; init; }

    public string? DefaultValue { get; init; }
}
