package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpFieldDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpFieldDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpField", label.value());
    }

    @Test
    void shouldDeclareFieldProperties() throws Exception {
        assertNotNull(CSharpFieldDescriptor.class.getMethod("getType"));
        assertTrue(CSharpFieldDescriptor.class.getMethod("getType")
                .isAnnotationPresent(Property.class));
    }
}
