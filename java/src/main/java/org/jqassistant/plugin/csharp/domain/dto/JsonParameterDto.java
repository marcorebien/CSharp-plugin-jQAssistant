package org.jqassistant.plugin.csharp.domain.dto;

public class JsonParameterDto {
    public String name;
    public String type;
    public String modifier;     // "ref"/"out"/...
    public boolean optional;
    public String defaultValue; // nullable
}
