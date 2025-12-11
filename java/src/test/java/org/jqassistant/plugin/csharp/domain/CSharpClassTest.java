package org.jqassistant.plugin.csharp.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSharpClassTest {

    @Test
    void shouldCreateClassWithName() {
        CSharpClass cls = new CSharpClass("OrderService");

        assertEquals("OrderService", cls.getName());
        assertNotNull(cls.getMethods());
        assertNotNull(cls.getInterfaces());
        assertNotNull(cls.getFields());
        assertNotNull(cls.getProperties());
    }

    @Test
    void shouldAddMethodsFieldsAndProperties() {
        CSharpClass cls = new CSharpClass("Customer");

        cls.addMethod(new CSharpMethod("GetName", "string"));
        cls.addField(new CSharpField("id", "int"));
        cls.addProperty(new CSharpProperty("FirstName", "string"));

        assertEquals(1, cls.getMethods().size());
        assertEquals(1, cls.getFields().size());
        assertEquals(1, cls.getProperties().size());
    }

    @Test
    void shouldStoreBaseClassAndInterfaces() {
        CSharpClass cls = new CSharpClass("Employee");

        cls.setBaseClass("Person");
        cls.addInterface("IDomainObject");

        assertEquals("Person", cls.getBaseClass());
        assertEquals(1, cls.getInterfaces().size());
        assertEquals("IDomainObject", cls.getInterfaces().get(0));
    }
}
