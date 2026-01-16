package org.jqassistant.plugin.csharp.domain.dto;

import java.util.List;

public class JsonInterfaceDto extends JsonTypeDto {
    public List<String> interfaces;
    public List<JsonMethodDto> methods;
}
