package org.jqassistant.plugin.csharp.domain.dto;

import java.util.List;

public class JsonMethodDto {
    public String name;
    public String returnType;
    public String visibility;
    public List<String> modifiers;
    public List<JsonParameterDto> parameters;
}

