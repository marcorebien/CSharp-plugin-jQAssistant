using CSharpAnalyzer.Analyzer;
using CSharpAnalyzer.Serialization;

static void Usage()
{
    Console.Error.WriteLine("Usage: CSharpAnalyzer <path-to.sln-or.csproj> [--out <file>] [--pretty]");
}

try
{
    if (args.Length == 0)
    {
        Usage();
        return 2;
    }

    var inputPath = args[0];
    string? outFile = null;
    var pretty = false;

    for (var i = 1; i < args.Length; i++)
    {
        if (args[i] == "--pretty") pretty = true;

        if (args[i] == "--out" && i + 1 < args.Length)
        {
            outFile = args[i + 1];
            i++;
        }
    }

    var analyzer = new RoslynAnalyzer();
    var model = await analyzer.AnalyzeAsync(inputPath);

    if (!string.IsNullOrWhiteSpace(outFile))
    {
        JsonExporter.WriteToFile(model, outFile, pretty);
        Console.WriteLine(outFile); // optional: Java kann Pfad lesen
    }
    else
    {
        Console.WriteLine(JsonExporter.ToJson(model, pretty));
    }

    return 0;
}
catch (Exception ex)
{
    Console.Error.WriteLine(ex);
    return 1;
}
