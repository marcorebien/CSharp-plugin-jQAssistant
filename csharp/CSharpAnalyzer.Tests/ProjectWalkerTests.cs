using System.Linq;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Xunit;
using CSharpAnalyzer.Analyzer;
using CSharpAnalyzer.Model;

using TypeKind = CSharpAnalyzer.Model.TypeKind;




public class ProjectWalkerTests
{

[Fact]
public void BuildProjectModel_ExtractsNamespacesAndTypes()
{
    var code1 = @"
namespace Demo.App;

public class A { }
public interface I { }
public record R(int X);
";

    var code2 = @"
namespace Demo.App.Utils;

public struct S { public int X; }
public enum E { One, Two }
";

    var compilation = CreateCompilation(code1, code2);

    var walker = new ProjectWalker();
    var model = walker.BuildProjectModel(compilation);

    Assert.NotNull(model);
    Assert.True(model.Namespaces.Count >= 2);

    var allTypes = model.Namespaces.SelectMany(n => n.Types).ToList();

    Assert.Contains(allTypes, t => t.FullName == "Demo.App.A" && t.Kind == TypeKind.Class);
    Assert.Contains(allTypes, t => t.FullName == "Demo.App.I" && t.Kind == TypeKind.Interface);
    Assert.Contains(allTypes, t => t.FullName == "Demo.App.R" && t.Kind == TypeKind.Record);

    Assert.Contains(allTypes, t => t.FullName == "Demo.App.Utils.S" && t.Kind == TypeKind.Struct);
    Assert.Contains(allTypes, t => t.FullName == "Demo.App.Utils.E" && t.Kind == TypeKind.Enum);
}


    [Fact]
    public void BuildProjectModel_MethodSignature_IsStable()
    {
        var code = @"
namespace Demo;
public class A
{
    public void M(int x) { }
    public static string N(ref int y) => ""x"";
}";

        var compilation = CreateCompilation(code);
        var model = new ProjectWalker().BuildProjectModel(compilation);

        var a = model.Namespaces.SelectMany(n => n.Types).Single(t => t.FullName == "Demo.A");
        var classModel = Assert.IsType<ClassModel>(a);

        Assert.Contains(classModel.Methods, m => m.Signature == "M(int x): void");
        Assert.Contains(classModel.Methods, m => m.Signature.Contains("N(") && m.Signature.EndsWith("): string"));
    }

    private static Compilation CreateCompilation(params string[] sources)
    {
        var trees = sources
            .Select(source => Microsoft.CodeAnalysis.CSharp.CSharpSyntaxTree.ParseText(source))
            .ToArray();

        var refs = new[]
        {
            MetadataReference.CreateFromFile(typeof(object).Assembly.Location),
            MetadataReference.CreateFromFile(typeof(Enumerable).Assembly.Location),
        };

        return Microsoft.CodeAnalysis.CSharp.CSharpCompilation.Create(
            assemblyName: "TestAsm",
            syntaxTrees: trees,
            references: refs,
            options: new Microsoft.CodeAnalysis.CSharp.CSharpCompilationOptions(
                OutputKind.DynamicallyLinkedLibrary));
    }


}
