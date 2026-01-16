namespace CSharpAnalyzer.Model;

public sealed class PropertyModel
{
    public string Name { get; init; } = null!;
    public string Type { get; init; } = null!;
    public bool HasGetter { get; init; }
    public bool HasSetter { get; init; }
}
