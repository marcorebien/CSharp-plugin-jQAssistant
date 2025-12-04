package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpTypeDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpTypeDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpType", label.value());
    }

    @Test
    void shouldHaveNameProperty() throws Exception {
        assertNotNull(CSharpTypeDescriptor.class.getMethod("getName"));
        assertTrue(CSharpTypeDescriptor.class.getMethod("getName")
                .isAnnotationPresent(Property.class));

        assertNotNull(CSharpTypeDescriptor.class.getMethod("setName", String.class));
    }

    @Test
    void shouldHaveNamespaceProperty() throws Exception {
        assertNotNull(CSharpTypeDescriptor.class.getMethod("getNamespace"));
        assertTrue(CSharpTypeDescriptor.class.getMethod("getNamespace")
                .isAnnotationPresent(Property.class));
    }

    @Test
    void shouldHaveVisibilityProperty() throws Exception {
        assertNotNull(CSharpTypeDescriptor.class.getMethod("getVisibility"));
        assertTrue(CSharpTypeDescriptor.class.getMethod("getVisibility")
                .isAnnotationPresent(Property.class));
    }
}
