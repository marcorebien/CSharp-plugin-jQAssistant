package org.jqassistant.plugin.csharp.mapper;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class DescriptorProxyFactory {

    private DescriptorProxyFactory() {}

    public static <T> T create(Class<T> descriptorType) {
        Objects.requireNonNull(descriptorType, "descriptorType");

        Map<String, Object> state = new ConcurrentHashMap<>();

        InvocationHandler handler = (proxy, method, args) -> {
            String name = method.getName();

            // Handle Object methods
            if (name.equals("toString") && method.getParameterCount() == 0) {
                return descriptorType.getSimpleName() + state;
            }
            if (name.equals("hashCode") && method.getParameterCount() == 0) {
                return System.identityHashCode(proxy);
            }
            if (name.equals("equals") && method.getParameterCount() == 1) {
                return proxy == args[0];
            }

            // getters like getX()
            if (name.startsWith("get") && method.getParameterCount() == 0) {
                String key = decapitalize(name.substring(3));
                return getOrInit(state, key, method.getReturnType(), method.getGenericReturnType());
            }

            // setters like setX(val)
            if (name.startsWith("set") && method.getParameterCount() == 1) {
                String key = decapitalize(name.substring(3));
                Object value = args[0];

                // ✅ FIX: ConcurrentHashMap erlaubt keine null values
                if (value == null) {
                    state.remove(key);
                } else {
                    state.put(key, value);
                }
                return null;
            }

            // boolean getters like isX()
            if (name.startsWith("is") && method.getParameterCount() == 0) {
                String key = decapitalize(name.substring(2));
                Object v = state.get(key);
                if (v == null) {
                    // default false
                    return false;
                }
                return v;
            }

            // custom boolean getter "hasGetter"/"hasSetter"
            // custom boolean getter "hasGetter"/"hasSetter"
            if (name.startsWith("has") && method.getParameterCount() == 0) {
                String key = decapitalize(name); // "hasGetter" -> "hasGetter"
                Object v = state.get(key);
                if (v == null) return false;
                return v;
            }


            // If the method returns List, initialize anyway (relation lists)
            if (List.class.isAssignableFrom(method.getReturnType()) && method.getParameterCount() == 0) {
                String key = name; // fallback unique
                return state.computeIfAbsent(key, k -> new ArrayList<>());
            }

            throw new UnsupportedOperationException("Unsupported method in proxy: " + method);
        };

        Object proxy = Proxy.newProxyInstance(
                descriptorType.getClassLoader(),
                new Class[]{ descriptorType },
                handler
        );
        return descriptorType.cast(proxy);
    }

    private static Object getOrInit(Map<String, Object> state,
                                    String key,
                                    Class<?> returnType,
                                    Type genericReturnType) {

        Object existing = state.get(key);
        if (existing != null) return existing;

        // ✅ FIX: niemals null in ConcurrentHashMap speichern
        Object init;
        if (List.class.isAssignableFrom(returnType)) {
            init = new ArrayList<>();
            state.put(key, init);
            return init;
        }

        if (returnType == Boolean.class || returnType == boolean.class) {
            init = false;
            state.put(key, init);
            return init;
        }

        // Für alle anderen Typen: "nicht vorhanden" -> null zurückgeben,
        // aber NICHT in die Map schreiben!
        return null;
    }

    private static String decapitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        if (s.length() == 1) return s.toLowerCase(Locale.ROOT);
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
