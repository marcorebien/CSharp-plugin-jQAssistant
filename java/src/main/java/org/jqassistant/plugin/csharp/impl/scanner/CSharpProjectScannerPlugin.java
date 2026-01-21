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
import org.jqassistant.plugin.csharp.impl.scanner.roslyn.DefaultRoslynAnalyzerRunner;
import org.jqassistant.plugin.csharp.impl.scanner.roslyn.RoslynAnalyzerRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

import static com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;

@Requires(FileDescriptor.class)
public class CSharpProjectScannerPlugin extends AbstractScannerPlugin<FileResource, CSharpProjectDescriptor> {

    public static final String PROP_ANALYZER_CSPROJ = "jqassistant.csharp.analyzer.csproj";

    private final JsonProjectImporter importer;
    private final CSharpDomainToDescriptorMapper mapper;
    private final RoslynAnalyzerRunner runner;

    public CSharpProjectScannerPlugin() {
        this(new JsonProjectImporter(), new CSharpDomainToDescriptorMapper(), new DefaultRoslynAnalyzerRunner());
    }

    // visible for tests
    public CSharpProjectScannerPlugin(JsonProjectImporter importer,
                                      CSharpDomainToDescriptorMapper mapper,
                                      RoslynAnalyzerRunner runner) {
        this.importer = Objects.requireNonNull(importer);
        this.mapper = Objects.requireNonNull(mapper);
        this.runner = Objects.requireNonNull(runner);
    }

    @Override
    public boolean accepts(FileResource item, String path, Scope scope) {
        if (!(scope instanceof CSharpScope)) return false;
        if (scope != CSharpScope.PROJECT) return false;
        if (path == null) return false;
        String p = path.toLowerCase(Locale.ROOT);
        return p.endsWith(".sln") || p.endsWith(".csproj");
    }

    @Override
    public CSharpProjectDescriptor scan(FileResource item, String x, Scope scope, Scanner scanner) throws IOException {
        Objects.requireNonNull(scanner, "scanner");
        Path target = item.getFile().toPath();

        String analyzerCsproj = System.getProperty(PROP_ANALYZER_CSPROJ);
        if (analyzerCsproj == null || analyzerCsproj.isBlank()) {
            throw new IllegalStateException("Missing system property: -D" + PROP_ANALYZER_CSPROJ + "=<path-to-CSharpAnalyzer.csproj>");
        }

        // ✅ HIER: runner statt RoslynInvoker direkt
        String json = runner.run(Path.of(analyzerCsproj), target);

        CSharpProject project;
        try (var in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            project = importer.importProject(in);
        }

        CSharpProjectDescriptor projectDesc = mapper.mapProject(project, scanner.getContext());
        projectDesc.setName(target.getFileName().toString());
        return projectDesc;
    }
}
