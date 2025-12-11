package org.jqassistant.plugin.csharp.api.descriptors;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSharpClassDescriptorTest {

    @Test
    void shouldHaveCorrectLabel() {
        Label label = CSharpClassDescriptor.class.getAnnotation(Label.class);
        assertNotNull(label);
        assertEquals("CSharpClass", label.value());
    }

    @Test
    void shouldExtendTypeDescriptor() {
        assertTrue(CSharpTypeDescriptor.class.isAssignableFrom(CSharpClassDescriptor.class));
    }

    @Test
    void shouldDeclareClassProperties() throws Exception {
        assertNotNull(CSharpClassDescriptor.class.getMethod("isAbstract"));
        assertTrue(CSharpClassDescriptor.class.getMethod("isAbstract")
                .isAnnotationPresent(Property.class));

        assertNotNull(CSharpClassDescriptor.class.getMethod("getBaseType"));
        assertTrue(CSharpClassDescriptor.class.getMethod("getBaseType")
                .isAnnotationPresent(Property.class));
    }
}
