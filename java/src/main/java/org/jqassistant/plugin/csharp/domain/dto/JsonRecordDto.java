package org.jqassistant.plugin.csharp.domain.dto;

import java.util.List;

public class JsonRecordDto extends JsonTypeDto {
    public String baseClass;
    public List<String> interfaces;
    public List<JsonMethodDto> methods;
    public List<JsonFieldDto> fields;
    public List<JsonPropertyDto> properties;
}
