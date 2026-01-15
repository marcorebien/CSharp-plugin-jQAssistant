using System.Linq;
using System.Text.Json;
using Xunit;
using CSharpAnalyzer.Model;
using CSharpAnalyzer.Serialization;

public class JsonExporterTests
{
    [Fact]
    public void ToJson_ProducesCamelCaseAndContainsNamespaces()
    {
        var model = new ProjectModel
        {
            Namespaces =
            {
                new NamespaceModel
                {
                    Name = "Demo",
                    Types =
                    {
                        new ClassModel
                        {
                            Name = "A",
                            Namespace = "Demo",
                            FullName = "Demo.A",
                        }
                    }
                }
            }
        };

        var json = JsonExporter.ToJson(model, pretty: false);

        using var doc = JsonDocument.Parse(json);
        var root = doc.RootElement;

        Assert.True(root.TryGetProperty("namespaces", out var namespaces));
        Assert.Equal("Demo", namespaces[0].GetProperty("name").GetString());

        var types = namespaces[0].GetProperty("types");
        Assert.Equal("Demo.A", types[0].GetProperty("fullName").GetString());
    }
}