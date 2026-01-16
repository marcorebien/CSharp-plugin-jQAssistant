package org.jqassistant.plugin.csharp.domain.dto;

import java.util.List;

public class JsonStructDto extends JsonTypeDto {
    public List<JsonMethodDto> methods;
    public List<JsonFieldDto> fields;
    public List<JsonPropertyDto> properties;
}
