package org.jqassistant.plugin.csharp.domain.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonClassDto.class, name = "class"),
        @JsonSubTypes.Type(value = JsonInterfaceDto.class, name = "interface"),
        @JsonSubTypes.Type(value = JsonStructDto.class, name = "struct"),
        @JsonSubTypes.Type(value = JsonEnumDto.class, name = "enum"),
        @JsonSubTypes.Type(value = JsonRecordDto.class, name = "record")
})
public abstract class JsonTypeDto {
    public String type;
    public String kind;
    public String name;
    public String namespace;
    public String fullName;
    public String visibility;
    public List<String> modifiers;
}

