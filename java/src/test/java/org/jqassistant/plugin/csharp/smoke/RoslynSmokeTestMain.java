package org.jqassistant.plugin.csharp.smoke;

import org.jqassistant.plugin.csharp.impl.scanner.roslyn.RoslynInvoker;

import java.nio.file.Path;

public class RoslynSmokeTestMain {

    public static void main(String[] args) {

        // Pfad zum C# Analyzer (.csproj)
        Path analyzerCsproj = Path.of(
                "D:/GitHub/CSharp-plugin-jQAssistant/csharp/src/CSharpAnalyzer/CSharpAnalyzer.csproj"
        );

        // Pfad zum Testprojekt, das analysiert werden soll
        Path targetProject = Path.of(
                "D:/GitHub/CSharp-plugin-jQAssistant/csharp/CSharpAnalyzer.Tests/TestProjects/SampleApp/src/SampleApp/SampleApp.csproj"
        );

        System.out.println("=== Starting Roslyn Smoke Test ===");
        System.out.println("Analyzer: " + analyzerCsproj);
        System.out.println("Target  : " + targetProject);

        RoslynInvoker invoker = new RoslynInvoker(analyzerCsproj);

        String json = invoker.runAnalyzer(targetProject);

        System.out.println("\n=== JAVA RECEIVED JSON (first 1000 chars) ===");
        System.out.println(json.substring(0, Math.min(1000, json.length())));

        System.out.println("\n=== Smoke Test Finished Successfully ===");
    }
}
