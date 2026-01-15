namespace CSharpAnalyzer.Model;

public sealed class NamespaceModel
{
    public string Name { get; init; } = null!;
    public List<TypeModel> Types { get; init; } = new();
}
