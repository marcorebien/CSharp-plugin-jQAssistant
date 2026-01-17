package org.jqassistant.plugin.csharp.impl.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;

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
     * System property to configure the analyzer csproj path.
     * Example:
     * -Djqassistant.csharp.analyzer.csproj=D:\GitHub\...\csharp\src\CSharpAnalyzer\CSharpAnalyzer.csproj
     */
    public static final String PROP_ANALYZER_CSPROJ = "jqassistant.csharp.analyzer.csproj";

    private final JsonProjectImporter importer;
    private final CSharpDomainToDescriptorMapper mapper;

    public CSharpProjectScannerPlugin() {
        this(new JsonProjectImporter(), new CSharpDomainToDescriptorMapper());
    }

    // visible for tests
    CSharpProjectScannerPlugin(JsonProjectImporter importer, CSharpDomainToDescriptorMapper mapper) {
        this.importer = Objects.requireNonNull(importer);
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        if (path == null) return false;
        String p = path.toLowerCase(Locale.ROOT);
        return p.endsWith(".sln") || p.endsWith(".csproj");
    }

    @Override
    public CSharpProjectDescriptor scan(FileResource item, String path, Scope scope, Scanner scanner) throws IOException {
        Objects.requireNonNull(scanner, "scanner");

        // 1) Analyzer-Pfad (csproj) aus System Property lesen
        String analyzerCsproj = System.getProperty(PROP_ANALYZER_CSPROJ);
        if (analyzerCsproj == null || analyzerCsproj.isBlank()) {
            throw new IllegalStateException("Missing system property: -" +
                    "D" + PROP_ANALYZER_CSPROJ + "=<path-to-CSharpAnalyzer.csproj>");
        }

        // 2) C# Analyzer ausführen -> JSON
        RoslynInvoker invoker = new RoslynInvoker(Path.of(analyzerCsproj));
        String json = invoker.runAnalyzer(Path.of(path));

        // 3) JSON -> Domain
        CSharpProject project;
        try (var in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            project = importer.importProject(in);
        }

        // 4) Domain -> Descriptor Graph
        CSharpProjectDescriptor projectDesc = mapper.mapProject(project, scanner.getContext());
        projectDesc.setName(Path.of(path).getFileName().toString());

        return projectDesc;
    }
}
