namespace CSharpAnalyzer.Model;

public sealed class InterfaceModel : TypeModel
{
    public List<string> Interfaces { get; init; } = new();
    public List<MethodModel> Methods { get; init; } = new();

    public InterfaceModel()
    {
        Kind = TypeKind.Interface;
    }
}
