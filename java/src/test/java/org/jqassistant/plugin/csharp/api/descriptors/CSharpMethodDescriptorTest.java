package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpMethodDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpMethodDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpMethod", label.value());
    }

    @Test
    void shouldDefineMethodProperties() throws Exception {
        assertNotNull(CSharpMethodDescriptor.class.getMethod("getName"));
        assertTrue(CSharpMethodDescriptor.class.getMethod("getName")
                .isAnnotationPresent(Property.class));

        assertNotNull(CSharpMethodDescriptor.class.getMethod("getReturnType"));
        assertTrue(CSharpMethodDescriptor.class.getMethod("getReturnType")
                .isAnnotationPresent(Property.class));
    }
}
