package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpInterfaceDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpInterfaceDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpInterface", label.value());
    }

    @Test
    void shouldExtendTypeDescriptor() {
        assertTrue(CSharpTypeDescriptor.class.isAssignableFrom(CSharpInterfaceDescriptor.class));
    }

    @Test
    void shouldHaveStaticProperty() throws Exception {
        assertNotNull(CSharpInterfaceDescriptor.class.getMethod("isStatic"));
        assertTrue(CSharpInterfaceDescriptor.class.getMethod("isStatic")
                .isAnnotationPresent(Property.class));
    }
}
