package org.jqassistant.plugin.csharp.domain.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonClassDto.class, name = "class"),
        @JsonSubTypes.Type(value = JsonInterfaceDto.class, name = "interface"),
        @JsonSubTypes.Type(value = JsonStructDto.class, name = "type"),
        @JsonSubTypes.Type(value = JsonEnumDto.class, name = "type"),
        @JsonSubTypes.Type(value = JsonRecordDto.class, name = "type")
})
public abstract class JsonTypeDto {
    public String type;       // discriminator from C# exporter: "class"|"interface"|"type"
    public String name;
    public String namespace;
    public String fullName;
    public String kind;       // "Class"|"Interface"|"Struct"|"Enum"|"Record" (string enum, camelCase maybe)
    public String visibility; // "Public"...
    public String modifiers;  // "Static, Abstract" (Flags as string)
}
