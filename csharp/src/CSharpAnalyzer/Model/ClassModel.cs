namespace CSharpAnalyzer.Model;

public sealed class ClassModel : TypeModel
{
    public string? BaseClass { get; init; }
    public List<string> Interfaces { get; init; } = new();

    public List<MethodModel> Methods { get; init; } = new();
    public List<FieldModel> Fields { get; init; } = new();
    public List<PropertyModel> Properties { get; init; } = new();

    public ClassModel()
    {
        Kind = TypeKind.Class;
    }
}
