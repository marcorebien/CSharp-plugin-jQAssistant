using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Xunit;
using CSharpAnalyzer.Analyzer;

public class RoslynAnalyzerIntegrationTests
{
    [Fact]
    public async Task AnalyzeAsync_OnCsproj_ReturnsExpectedTypes()
    {
        var csproj = Path.GetFullPath(
            Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "TestProjects","SampleApp", "src", "SampleApp", "SampleApp.csproj"));

        Assert.True(File.Exists(csproj), $"Missing test project: {csproj}");

        var analyzer = new RoslynAnalyzer();
        var model = await analyzer.AnalyzeAsync(csproj);

        var all = model.Namespaces.SelectMany(n => n.Types).ToList();
        Assert.Contains(all, t => t.FullName.EndsWith("SampleApp.Program"));
        Assert.Contains(all, t => t.FullName.EndsWith("SampleApp.Models.User"));
    }
}