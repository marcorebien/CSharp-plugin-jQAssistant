package org.jqassistant.plugin.csharp.impl.scanner.roslyn;

import java.nio.file.Path;
import java.util.Objects;

public class DefaultRoslynAnalyzerRunner implements RoslynAnalyzerRunner {

    @Override
    public String run(Path analyzerCsproj, Path target) {
        Objects.requireNonNull(analyzerCsproj, "analyzerCsproj");
        Objects.requireNonNull(target, "target");
        return new RoslynInvoker(analyzerCsproj).runAnalyzer(target);
    }
}
