package org.jqassistant.plugin.csharp.domain.dto;

import java.util.List;

public class JsonMethodDto {
    public String name;
    public String signature;
    public String returnType;
    public String visibility;
    public String modifiers; // flags string
    public List<JsonParameterDto> parameters;
}
