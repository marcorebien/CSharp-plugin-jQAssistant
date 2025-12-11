package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpNamespaceDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpNamespaceDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpNamespace", label.value());
    }

    @Test
    void shouldHaveNameProperty() throws Exception {
        assertTrue(CSharpNamespaceDescriptor.class
                .getMethod("getName")
                .isAnnotationPresent(Property.class));

        assertNotNull(CSharpNamespaceDescriptor.class.getMethod("setName", String.class));
    }
}
