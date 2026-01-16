package org.jqassistant.plugin.csharp.impl.scanner.roslyn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RoslynInvoker {

    private final Path analyzerCsproj;

    public RoslynInvoker(Path analyzerCsproj) {
        this.analyzerCsproj = analyzerCsproj;
    }

    public String runAnalyzer(Path solutionOrProjectToAnalyze) {
        try {
            // 1) Vorab prüfen, damit du sofort siehst ob Pfade stimmen
            if (analyzerCsproj == null || !Files.exists(analyzerCsproj)) {
                throw new IllegalStateException("Analyzer csproj not found: " + analyzerCsproj);
            }
            if (solutionOrProjectToAnalyze == null || !Files.exists(solutionOrProjectToAnalyze)) {
                throw new IllegalStateException("Target project not found: " + solutionOrProjectToAnalyze);
            }

            // 2) dotnet run --project <analyzer> -- <target>
            List<String> cmd = new ArrayList<>();
            cmd.add("dotnet");
            cmd.add("run");
            cmd.add("--project");
            cmd.add(analyzerCsproj.toAbsolutePath().toString());
            cmd.add("--configuration");
            cmd.add("Release");
            cmd.add("--");
            cmd.add(solutionOrProjectToAnalyze.toAbsolutePath().toString());

            ProcessBuilder pb = new ProcessBuilder(cmd);

            // super wichtig: Working dir auf Analyzer-Ordner setzen
            pb.directory(analyzerCsproj.getParent().toFile());

            // stderr mit stdout mergen, damit du alles siehst
            pb.redirectErrorStream(true);

            Process p = pb.start();

            StringBuilder out = new StringBuilder();
            try (BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = r.readLine()) != null) {
                    out.append(line).append(System.lineSeparator());
                }
            }

            int code = p.waitFor();
            if (code != 0) {
                throw new IllegalStateException("C# analyzer failed, exit code: " + code + "\nOutput:\n" + out);
            }

            return out.toString();

        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to invoke C# analyzer", e);
        }
    }
}
