package org.jqassistant.plugin.csharp.domain.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "kind"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonClassDto.class, name = "class"),
        @JsonSubTypes.Type(value = JsonInterfaceDto.class, name = "interface")
})
public abstract class JsonTypeDto {

    public String kind;
    public String name;
    public String namespace;
    public List<String> modifiers;
    public String visibility;
}
