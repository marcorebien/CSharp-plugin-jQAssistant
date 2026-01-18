package org.jqassistant.plugin.csharp.impl.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import org.jqassistant.plugin.csharp.api.CSharpScope;
import org.jqassistant.plugin.csharp.api.descriptors.CSharpProjectDescriptor;
import org.jqassistant.plugin.csharp.domain.CSharpProject;
import org.jqassistant.plugin.csharp.domain.JsonProjectImporter;
import org.jqassistant.plugin.csharp.impl.mapper.CSharpDomainToDescriptorMapper;
import org.jqassistant.plugin.csharp.impl.scanner.roslyn.RoslynInvoker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

import static com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;

@Requires(FileDescriptor.class)
public class CSharpProjectScannerPlugin extends AbstractScannerPlugin<FileResource, CSharpProjectDescriptor> {

    /**
     * Path to the C# analyzer csproj (the Roslyn CLI tool).
     * Example:
     * -Djqassistant.csharp.analyzer.csproj=D:\GitHub\...\csharp\src\CSharpAnalyzer\CSharpAnalyzer.csproj
     */
    public static final String PROP_ANALYZER_CSPROJ = "jqassistant.csharp.analyzer.csproj";

    /**
     * Friendly CLI entry points (Variante B):
     * -Dcsharp.project=...\MyApp.csproj
     * -Dcsharp.solution=...\MySolution.sln
     *
     * If these are set, the scanner uses them as target and does NOT depend on the scanned file path.
     */
    public static final String PROP_CSHARP_PROJECT  = "csharp.project";
    public static final String PROP_CSHARP_SOLUTION = "csharp.solution";

    private final JsonProjectImporter importer;
    private final CSharpDomainToDescriptorMapper mapper;

    public CSharpProjectScannerPlugin() {
        this(new JsonProjectImporter(), new CSharpDomainToDescriptorMapper());
    }

    // visible for tests
    CSharpProjectScannerPlugin(JsonProjectImporter importer, CSharpDomainToDescriptorMapper mapper) {
        this.importer = Objects.requireNonNull(importer, "importer");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        // We only run in our dedicated scope
//        if (!(scope instanceof CSharpScope) || scope != CSharpScope.PROJECT) {
//            return false;
//        }
        if (path == null) return false;
        String p = path.toLowerCase(Locale.ROOT);
        return p.endsWith(".sln") || p.endsWith(".csproj");


        // If user explicitly provides target via system property -> accept on any scanned file
        // (as long as something triggers scanning in this scope).
//        if (isConfiguredTargetViaProperty()) {
//            return true;
//        }

    }

    @Override
    public CSharpProjectDescriptor scan(FileResource item, String ignored, Scope scope, Scanner scanner) throws IOException {
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(scanner, "scanner");

        // 0) Determine target (.csproj/.sln)
        Path target = resolveTarget(item);

        // 1) Analyzer csproj
        Path analyzerCsproj = resolveAnalyzerCsproj();

        // 2) Run C# analyzer -> JSON
        RoslynInvoker invoker = createInvoker(analyzerCsproj);
        String json = invoker.runAnalyzer(target);

        // 3) JSON -> Domain
        CSharpProject project;
        try (var in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            project = importer.importProject(in);
        }

        // 4) Domain -> Descriptor graph
        CSharpProjectDescriptor projectDesc = mapper.mapProject(project, scanner.getContext());
        projectDesc.setName(target.getFileName().toString());

        return projectDesc;
    }

    /**
     * Hook for integration tests (so we can stub RoslynInvoker without running dotnet).
     */
    protected RoslynInvoker createInvoker(Path analyzerCsproj) {
        return new RoslynInvoker(analyzerCsproj);
    }

    private static boolean isConfiguredTargetViaProperty() {
        String proj = System.getProperty(PROP_CSHARP_PROJECT);
        String sln  = System.getProperty(PROP_CSHARP_SOLUTION);
        return (proj != null && !proj.isBlank()) || (sln != null && !sln.isBlank());
    }

    private static Path resolveTarget(FileResource item) throws IOException {
        String proj = System.getProperty(PROP_CSHARP_PROJECT);
        String sln  = System.getProperty(PROP_CSHARP_SOLUTION);

        if (proj != null && !proj.isBlank()) {
            return Path.of(proj);
        }
        if (sln != null && !sln.isBlank()) {
            return Path.of(sln);
        }

        // Fallback: use the scanned file
        return item.getFile().toPath();
    }

    private static Path resolveAnalyzerCsproj() {
        String analyzerCsproj = System.getProperty(PROP_ANALYZER_CSPROJ);
        if (analyzerCsproj == null || analyzerCsproj.isBlank()) {
            throw new IllegalStateException(
                    "Missing system property: -D" + PROP_ANALYZER_CSPROJ + "=<path-to-CSharpAnalyzer.csproj>"
            );
        }
        return Path.of(analyzerCsproj);
    }
}