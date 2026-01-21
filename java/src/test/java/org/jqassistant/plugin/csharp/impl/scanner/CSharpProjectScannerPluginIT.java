package org.jqassistant.plugin.csharp.impl.scanner;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import org.jqassistant.plugin.csharp.api.CSharpScope;
import org.jqassistant.plugin.csharp.api.descriptors.CSharpProjectDescriptor;
import org.jqassistant.plugin.csharp.impl.scanner.roslyn.RoslynAnalyzerRunner;
import org.jqassistant.plugin.csharp.mapper.DescriptorProxyFactory;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CSharpProjectScannerPluginIT {

    @Test
    void shouldScanProject_EndToEnd_withoutDotnet_usingRunnerStub() throws Exception {
        // JSON laden
        String json;
        try (InputStream in = getClass().getResourceAsStream("/importer/valid-full-project.json")) {
            assertNotNull(in, "Missing resource: /importer/valid-full-project.json");
            json = new String(in.readAllBytes());
        }

        // Property muss gesetzt sein (Scanner checkt das)
        Path fakeAnalyzerCsproj = Files.createTempFile("CSharpAnalyzer", ".csproj");
       System.setProperty(CSharpProjectScannerPlugin.PROP_ANALYZER_CSPROJ, fakeAnalyzerCsproj.toString());
//
//        // Target csproj file (FileResource liefert diesen Pfad)
       Path target = Files.createTempFile("SampleApp", ".csproj");

       FileResource fileResource = mock(FileResource.class);
       when(fileResource.getFile()).thenReturn(target.toFile());

//        // Scanner -> Context -> Store
        Store store = new ProxyStore();

        ScannerContext ctx = mock(ScannerContext.class);
        when(ctx.getStore()).thenReturn(store);

        Scanner scanner = mock(Scanner.class);
        when(scanner.getContext()).thenReturn(ctx);
//
//        // Runner stub (statt dotnet)
        RoslynAnalyzerRunner runner = (analyzerCsproj, t) -> json;

//        // Plugin mit runner stub
        CSharpProjectScannerPlugin plugin = new CSharpProjectScannerPlugin(
                new org.jqassistant.plugin.csharp.domain.JsonProjectImporter(),
                new org.jqassistant.plugin.csharp.impl.mapper.CSharpDomainToDescriptorMapper(),
                runner
        );

        assertTrue(plugin.accepts(fileResource, target.toString(), CSharpScope.PROJECT));

        CSharpProjectDescriptor out = plugin.scan(fileResource, target.toString(), CSharpScope.PROJECT, scanner);

        assertNotNull(out);
        assertEquals(target.getFileName().toString(), out.getName());
        assertNotNull(out.getNamespaces());
        assertFalse(out.getNamespaces().isEmpty());
        assertFalse(out.getNamespaces().get(0).getTypes().isEmpty());
    }

    private static final class ProxyStore implements Store {
        @Override
        public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor> T create(Class<T> type) {
            Objects.requireNonNull(type, "type");
            return DescriptorProxyFactory.create(type);
        }

        @Override public void start() {}
        @Override public void stop() {}
        @Override public com.buschmais.xo.api.XOManager getXOManager() { throw new UnsupportedOperationException(); }
        @Override public void reset() { throw new UnsupportedOperationException(); }
        @Override public void beginTransaction() { throw new UnsupportedOperationException(); }
        @Override public void commitTransaction() { throw new UnsupportedOperationException(); }
        @Override public void rollbackTransaction() { throw new UnsupportedOperationException(); }
        @Override public boolean hasActiveTransaction() { return false; }
        @Override public void flush() { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor> T create(Class<T> type, com.buschmais.xo.api.Example<T> example) { throw new UnsupportedOperationException(); }
        @Override public <S extends com.buschmais.jqassistant.core.store.api.model.Descriptor, R extends com.buschmais.jqassistant.core.store.api.model.Descriptor, T extends com.buschmais.jqassistant.core.store.api.model.Descriptor> R create(S source, Class<R> relationType, T target) { throw new UnsupportedOperationException(); }
        @Override public <S extends com.buschmais.jqassistant.core.store.api.model.Descriptor, R extends com.buschmais.jqassistant.core.store.api.model.Descriptor, T extends com.buschmais.jqassistant.core.store.api.model.Descriptor> R create(S source, Class<R> relationType, T target, com.buschmais.xo.api.Example<R> example) { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor> T create(Class<T> type, String fullQualifiedName) { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor> void delete(T descriptor) { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor, N extends com.buschmais.jqassistant.core.store.api.model.Descriptor> N addDescriptorType(T descriptor, Class<?> newDescriptorType, Class<N> as) { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor, N extends com.buschmais.jqassistant.core.store.api.model.Descriptor> N addDescriptorType(T descriptor, Class<N> newDescriptorType) { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor, N extends com.buschmais.jqassistant.core.store.api.model.Descriptor> N removeDescriptorType(T descriptor, Class<?> obsoleteDescriptorType, Class<N> as) { throw new UnsupportedOperationException(); }
        @Override public <T extends com.buschmais.jqassistant.core.store.api.model.Descriptor> T find(Class<T> type, String value) { throw new UnsupportedOperationException(); }
        @Override public com.buschmais.xo.api.Query.Result<com.buschmais.xo.api.Query.Result.CompositeRowObject> executeQuery(String query, java.util.Map<String, Object> parameters) { throw new UnsupportedOperationException(); }
        @Override public com.buschmais.xo.api.Query.Result<com.buschmais.xo.api.Query.Result.CompositeRowObject> executeQuery(String query) { throw new UnsupportedOperationException(); }
        @Override public <Q> com.buschmais.xo.api.Query.Result<Q> executeQuery(Class<Q> query, java.util.Map<String, Object> parameters) { throw new UnsupportedOperationException(); }
        @Override public <K, V extends com.buschmais.jqassistant.core.store.api.model.Descriptor> com.github.benmanes.caffeine.cache.Cache<K, V> getCache(String cacheKey) { throw new UnsupportedOperationException(); }
        @Override public void invalidateCache(String cacheKey) { throw new UnsupportedOperationException(); }
        @Override public <E extends Exception> void requireTransaction(TransactionalAction<E> transactionalAction) throws E { throw new UnsupportedOperationException(); }
        @Override public <T, E extends Exception> T requireTransaction(TransactionalSupplier<T, E> transactionalSupplier) throws E { throw new UnsupportedOperationException(); }
    }
}
