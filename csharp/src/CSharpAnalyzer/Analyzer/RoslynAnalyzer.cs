using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Build.Locator;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.MSBuild;
using CSharpAnalyzer.Model;

namespace CSharpAnalyzer.Analyzer;

public sealed class RoslynAnalyzer
{
    private static bool _msbuildRegistered;

    public async Task<ProjectModel> AnalyzeAsync(string solutionOrProjectPath, CancellationToken ct = default)
    {
        if (string.IsNullOrWhiteSpace(solutionOrProjectPath))
            throw new ArgumentException("Path must not be empty.", nameof(solutionOrProjectPath));

        RegisterMSBuildOnce();

        using var workspace = MSBuildWorkspace.Create();

        // Obsolete warning kannst du ignorieren oder später modernisieren.
        workspace.RegisterWorkspaceFailedHandler(e =>
        {
            Console.Error.WriteLine($"[MSBUILD] {e.Diagnostic.Kind}: {e.Diagnostic.Message}");
        });

        var projects = await LoadProjectsAsync(workspace, solutionOrProjectPath, ct);
        if (projects.Count == 0)
            throw new InvalidOperationException("No projects found to analyze.");

        var walker = new ProjectWalker();

        // Merge-Maps (local mutable, output bleibt init-only sauber)
        var nsMap = new Dictionary<string, List<TypeModel>>(StringComparer.Ordinal);
        var seenTypes = new HashSet<string>(StringComparer.Ordinal);

        foreach (var project in projects)
        {
            ct.ThrowIfCancellationRequested();

            var compilation = await project.GetCompilationAsync(ct);
            if (compilation is null)
            {
                Console.Error.WriteLine($"[WARN] Compilation failed for project: {project.Name}");
                continue;
            }

            var model = walker.BuildProjectModel(compilation);

            foreach (var ns in model.Namespaces)
            {
                if (!nsMap.TryGetValue(ns.Name, out var list))
                {
                    list = new List<TypeModel>();
                    nsMap.Add(ns.Name, list);
                }

                foreach (var t in ns.Types)
                {
                    if (string.IsNullOrWhiteSpace(t.FullName)) continue;
                    if (seenTypes.Add(t.FullName))
                        list.Add(t);
                }
            }
        }

        // ✅ init-only korrekt: alles im Object Initializer
        return new ProjectModel
        {
            Namespaces = nsMap
                .OrderBy(kvp => kvp.Key, StringComparer.Ordinal)
                .Select(kvp => new NamespaceModel
                {
                    Name = kvp.Key,
                    Types = kvp.Value.OrderBy(t => t.FullName, StringComparer.Ordinal).ToList()
                })
                .ToList()
        };
    }

    private static void RegisterMSBuildOnce()
    {
        if (_msbuildRegistered) return;
        MSBuildLocator.RegisterDefaults();
        _msbuildRegistered = true;
    }

    private static async Task<List<Project>> LoadProjectsAsync(MSBuildWorkspace workspace, string path, CancellationToken ct)
    {
        if (path.EndsWith(".sln", StringComparison.OrdinalIgnoreCase))
        {
            var solution = await workspace.OpenSolutionAsync(path, cancellationToken: ct);
            return solution.Projects.ToList();
        }

        if (path.EndsWith(".csproj", StringComparison.OrdinalIgnoreCase))
        {
            var project = await workspace.OpenProjectAsync(path, cancellationToken: ct);
            return new List<Project> { project };
        }

        throw new ArgumentException("Path must be a .sln or .csproj.", nameof(path));
    }
}
