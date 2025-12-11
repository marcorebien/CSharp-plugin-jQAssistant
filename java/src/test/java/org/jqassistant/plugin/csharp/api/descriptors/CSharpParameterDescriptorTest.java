package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpParameterDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpParameterDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpParameter", label.value());
    }

    @Test
    void shouldDeclareParameterProperties() throws Exception {
        assertNotNull(CSharpParameterDescriptor.class.getMethod("getType"));
        assertTrue(CSharpParameterDescriptor.class.getMethod("getType")
                .isAnnotationPresent(Property.class));

        assertNotNull(CSharpParameterDescriptor.class.getMethod("getPosition"));
        assertTrue(CSharpParameterDescriptor.class.getMethod("getPosition")
                .isAnnotationPresent(Property.class));
    }
}
