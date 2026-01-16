namespace CSharpAnalyzer.Model;

public sealed class EnumModel : TypeModel
{
    public List<string> Members { get; init; } = new();

    public EnumModel()
    {
        Kind = TypeKind.Enum;
    }
}
