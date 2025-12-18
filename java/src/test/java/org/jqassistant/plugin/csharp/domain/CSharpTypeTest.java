package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpTypeTest {

    static class TestType extends CSharpType {
        TestType(String name, String namespace) {
            super(name, namespace);
        }
    }

    @Test
    void shouldReturnFullyQualifiedName() {
        CSharpType type = new TestType("MyClass", "My.Namespace");

        assertEquals("My.Namespace.MyClass", type.getFullName());
    }

    @Test
    void shouldHandleEmptyNamespace() {
        CSharpType type = new TestType("MyClass", "");

        assertEquals("MyClass", type.getFullName());
    }

    @Test
    void shouldDefaultVisibilityToInternal() {
        CSharpType type = new TestType("MyClass", "Test");

        assertEquals(CSharpVisibility.INTERNAL, type.getVisibility());
    }

    @Test
    void shouldAllowSettingVisibility() {
        CSharpType type = new TestType("MyClass", "Test");

        type.setVisibility(CSharpVisibility.PUBLIC);

        assertEquals(CSharpVisibility.PUBLIC, type.getVisibility());
    }
}
