package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpPropertyDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpPropertyDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpProperty", label.value());
    }

    @Test
    void shouldDefinePropertyAttributes() throws Exception {
        assertNotNull(CSharpPropertyDescriptor.class.getMethod("getType"));
        assertTrue(CSharpPropertyDescriptor.class.getMethod("getType")
                .isAnnotationPresent(Property.class));
    }
}
