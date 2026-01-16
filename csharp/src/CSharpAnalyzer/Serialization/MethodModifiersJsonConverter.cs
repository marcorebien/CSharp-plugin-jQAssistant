using System;
using System.Text.Json;
using System.Text.Json.Serialization;
using CSharpAnalyzer.Model;

namespace CSharpAnalyzer.Serialization;

/// <summary>
/// Serializes MethodModifiers (flags enum) as a JSON string array,
/// e.g. ["static","async"] instead of "Static, Async" or numeric values.
/// </summary>
public sealed class MethodModifiersJsonConverter : JsonConverter<MethodModifiers>
{
    public override MethodModifiers Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        => throw new NotSupportedException("Deserialization is not required for analyzer output.");

    public override void Write(Utf8JsonWriter writer, MethodModifiers value, JsonSerializerOptions options)
    {
        writer.WriteStartArray();

        if (value.HasFlag(MethodModifiers.Static)) writer.WriteStringValue("static");
        if (value.HasFlag(MethodModifiers.Abstract)) writer.WriteStringValue("abstract");
        if (value.HasFlag(MethodModifiers.Virtual)) writer.WriteStringValue("virtual");
        if (value.HasFlag(MethodModifiers.Override)) writer.WriteStringValue("override");
        if (value.HasFlag(MethodModifiers.Sealed)) writer.WriteStringValue("sealed");
        if (value.HasFlag(MethodModifiers.Async)) writer.WriteStringValue("async");

        writer.WriteEndArray();
    }
}