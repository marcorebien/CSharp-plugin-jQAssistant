package org.jqassistant.plugin.csharp.impl.scanner.roslyn;

import java.nio.file.Path;

public interface RoslynAnalyzerRunner {
    String run(Path analyzerCsproj, Path target);
}
