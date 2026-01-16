namespace CSharpAnalyzer.Model;

public sealed class ProjectModel
{
    public List<NamespaceModel> Namespaces { get; init; } = new();
}
