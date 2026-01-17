package org.jqassistant.plugin.csharp.mapper;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.api.Example;
import com.buschmais.xo.api.Query;
import com.buschmais.xo.api.XOManager;
import com.github.benmanes.caffeine.cache.Cache;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public final class TestScannerContext {

    private TestScannerContext() {}

    public static ScannerContext create() {
        Store store = new ProxyStore();
        return new ScannerContext() {
            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public Store getStore() {
                return store;
            }

            @Override
            public <T> void push(Class<T> key, T value) {

            }

            @Override
            public <T> T peek(Class<T> key) {
                return null;
            }

            @Override
            public <T> T peekOrDefault(Class<T> key, T defaultValue) {
                return null;
            }

            @Override
            public <T> T pop(Class<T> key) {
                return null;
            }

            @Override
            public <D extends Descriptor> void setCurrentDescriptor(D descriptor) {

            }

            @Override
            public <D extends Descriptor> D getCurrentDescriptor() {
                return null;
            }

            @Override
            public File getDataDirectory(String path) {
                return null;
            }

            @Override
            public File getWorkingDirectory() {
                return null;
            }
        };
    }

    private static final class ProxyStore implements Store {

        @Override
        public void start() { /* not needed */ }

        @Override
        public void stop() { /* not needed */ }

        @Override
        public void reset() { /* not needed */ }

        @Override
        public void beginTransaction() { /* not needed */ }

        @Override
        public void commitTransaction() { /* not needed */ }

        @Override
        public void rollbackTransaction() { /* not needed */ }

        @Override
        public boolean hasActiveTransaction() { return false; }

        @Override
        public void flush() { /* not needed */ }

        // -------------------------
        // The only thing the mapper uses:
        // store.create(DescriptorClass)
        // -------------------------

        @Override
        public <T extends Descriptor> T create(Class<T> type) {
            Objects.requireNonNull(type, "type");
            return DescriptorProxyFactory.create(type);
        }

        @Override
        public <T extends Descriptor> T create(Class<T> type, Example<T> example) {
            // for our mapper tests, example is irrelevant
            return create(type);
        }

        @Override
        public <S extends Descriptor, R extends Descriptor, T extends Descriptor> R create(
                S source, Class<R> relationType, T target
        ) {
            // If you ever start using relation descriptors (edge as node), you can support it.
            // For now: create relation descriptor instance.
            return create(relationType);
        }

        @Override
        public <S extends Descriptor, R extends Descriptor, T extends Descriptor> R create(
                S source, Class<R> relationType, T target, Example<R> example
        ) {
            return create(relationType);
        }

        @Override
        public <T extends FullQualifiedNameDescriptor> T create(Class<T> type, String fullQualifiedName) {
            // Mapper nutzt das vermutlich (noch) nicht, aber ist easy zu unterstützen.
            T d = create(type);
            d.setFullQualifiedName(fullQualifiedName);
            return d;
        }

        // -------------------------
        // Everything else: fail fast
        // -------------------------

        @Override
        public XOManager getXOManager() {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <T extends Descriptor> void delete(T descriptor) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <T extends Descriptor, N extends Descriptor> N addDescriptorType(T descriptor, Class<?> newDescriptorType, Class<N> as) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <T extends Descriptor, N extends Descriptor> N addDescriptorType(T descriptor, Class<N> newDescriptorType) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <T extends Descriptor, N extends Descriptor> N removeDescriptorType(T descriptor, Class<?> obsoleteDescriptorType, Class<N> as) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <T extends Descriptor> T find(Class<T> type, String value) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public Query.Result<Query.Result.CompositeRowObject> executeQuery(String query, Map<String, Object> parameters) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public Query.Result<Query.Result.CompositeRowObject> executeQuery(String query) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <Q> Query.Result<Q> executeQuery(Class<Q> query, Map<String, Object> parameters) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <K, V extends Descriptor> Cache<K, V> getCache(String cacheKey) {
            // Mapper braucht das nicht. Return null führt zu NPEs an anderer Stelle, also fail fast:
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public void invalidateCache(String cacheKey) {
            throw new UnsupportedOperationException("Not needed in mapper unit tests.");
        }

        @Override
        public <E extends Exception> void requireTransaction(TransactionalAction<E> transactionalAction) throws E {
            // For unit tests: just execute directly
            transactionalAction.execute();
        }

        @Override
        public <T, E extends Exception> T requireTransaction(TransactionalSupplier<T, E> transactionalSupplier) throws E {
            return transactionalSupplier.execute();
        }
    }

}
