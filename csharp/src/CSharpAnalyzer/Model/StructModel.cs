namespace CSharpAnalyzer.Model;

public sealed class StructModel : TypeModel
{
    public List<MethodModel> Methods { get; init; } = new();
    public List<FieldModel> Fields { get; init; } = new();
    public List<PropertyModel> Properties { get; init; } = new();

    public StructModel()
    {
        Kind = TypeKind.Struct;
    }
}
