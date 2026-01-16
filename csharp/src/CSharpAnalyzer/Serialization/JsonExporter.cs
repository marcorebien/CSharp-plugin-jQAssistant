using System.Text.Encodings.Web;
using System.Text.Json;
using System.Text.Json.Serialization;
using CSharpAnalyzer.Model;

namespace CSharpAnalyzer.Serialization;

public static class JsonExporter
{
    private static JsonSerializerOptions CreateOptions(bool indented)
    {
        var options = new JsonSerializerOptions
        {
            WriteIndented = indented,
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
            Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping
        };

        // Enums als Strings (camelCase)
        options.Converters.Add(new JsonStringEnumConverter(JsonNamingPolicy.CamelCase));

        // Polymorphie für TypeModel ohne Attribute
        options.Converters.Add(new TypeModelJsonConverter());
        
        options.Converters.Add(new MethodModifiersJsonConverter());

        return options;
    }

    public static string ToJson(ProjectModel model, bool pretty = false)
        => JsonSerializer.Serialize(model, CreateOptions(pretty));

    public static void WriteToFile(ProjectModel model, string outputPath, bool pretty = false)
        => File.WriteAllText(outputPath, ToJson(model, pretty));

    public static void WriteToStream(ProjectModel model, Stream output, bool pretty = false)
        => JsonSerializer.Serialize(output, model, CreateOptions(pretty));

    // -----------------------------
    // Converter: TypeModel -> { "kind": "...", ... }
    // -----------------------------
    private sealed class TypeModelJsonConverter : JsonConverter<TypeModel>
    {
        public override TypeModel? Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
            => throw new NotSupportedException("Deserialization is not required for the analyzer output.");

        public override void Write(Utf8JsonWriter writer, TypeModel value, JsonSerializerOptions options)
        {
            writer.WriteStartObject();

            // ✅ Single discriminator for Java: "type"
            writer.WriteString("type", ToKindString(value.Kind));

            // Base fields
            writer.WriteString("name", value.Name);
            writer.WriteString("namespace", value.Namespace);
            writer.WriteString("fullName", value.FullName);

            // ✅ write enums/flags in the same style the Java importer expects
            writer.WriteString("visibility", ToVisibilityString(value.Visibility));
            WriteTypeModifiers(writer, "modifiers", value.Modifiers);

            // Derived fields
            switch (value)
            {
                case ClassModel cm:
                    WriteClassLike(writer, cm, options);
                    break;

                case RecordModel rm:
                    WriteRecordLike(writer, rm, options);
                    break;

                case StructModel sm:
                    WriteStructLike(writer, sm, options);
                    break;

                case InterfaceModel im:
                    WriteInterfaceLike(writer, im, options);
                    break;

                case EnumModel em:
                    writer.WritePropertyName("members");
                    JsonSerializer.Serialize(writer, em.Members, options);
                    break;

                default:
                    // fallback: nothing extra
                    break;
            }

            writer.WriteEndObject();
        }

        private static void WriteClassLike(Utf8JsonWriter writer, ClassModel cm, JsonSerializerOptions options)
        {
            if (cm.BaseClass is not null)
                writer.WriteString("baseClass", cm.BaseClass);

            writer.WritePropertyName("interfaces");
            JsonSerializer.Serialize(writer, cm.Interfaces, options);

            writer.WritePropertyName("methods");
            JsonSerializer.Serialize(writer, cm.Methods, options);

            writer.WritePropertyName("fields");
            JsonSerializer.Serialize(writer, cm.Fields, options);

            writer.WritePropertyName("properties");
            JsonSerializer.Serialize(writer, cm.Properties, options);
        }

        private static void WriteRecordLike(Utf8JsonWriter writer, RecordModel rm, JsonSerializerOptions options)
        {
            if (rm.BaseClass is not null)
                writer.WriteString("baseClass", rm.BaseClass);

            writer.WritePropertyName("interfaces");
            JsonSerializer.Serialize(writer, rm.Interfaces, options);

            writer.WritePropertyName("methods");
            JsonSerializer.Serialize(writer, rm.Methods, options);

            writer.WritePropertyName("fields");
            JsonSerializer.Serialize(writer, rm.Fields, options);

            writer.WritePropertyName("properties");
            JsonSerializer.Serialize(writer, rm.Properties, options);
        }

        private static void WriteStructLike(Utf8JsonWriter writer, StructModel sm, JsonSerializerOptions options)
        {
            writer.WritePropertyName("methods");
            JsonSerializer.Serialize(writer, sm.Methods, options);

            writer.WritePropertyName("fields");
            JsonSerializer.Serialize(writer, sm.Fields, options);

            writer.WritePropertyName("properties");
            JsonSerializer.Serialize(writer, sm.Properties, options);
        }

        private static void WriteInterfaceLike(Utf8JsonWriter writer, InterfaceModel im, JsonSerializerOptions options)
        {
            writer.WritePropertyName("interfaces");
            JsonSerializer.Serialize(writer, im.Interfaces, options);

            writer.WritePropertyName("methods");
            JsonSerializer.Serialize(writer, im.Methods, options);
        }

        // ---- helpers: enum strings ----

        private static string ToKindString(TypeKind kind) => kind switch
        {
            TypeKind.Class => "class",
            TypeKind.Interface => "interface",
            TypeKind.Struct => "struct",
            TypeKind.Enum => "enum",
            TypeKind.Record => "record",
            _ => "class"
        };

        private static string ToVisibilityString(CSharpVisibility v) => v switch
        {
            CSharpVisibility.Public => "public",
            CSharpVisibility.Private => "private",
            CSharpVisibility.Protected => "protected",
            CSharpVisibility.Internal => "internal",
            CSharpVisibility.ProtectedInternal => "protectedInternal",
            _ => "internal"
        };

        private static void WriteTypeModifiers(Utf8JsonWriter writer, string propName, TypeModifiers mods)
        {
            writer.WritePropertyName(propName);
            writer.WriteStartArray();

            if (mods.HasFlag(TypeModifiers.Static)) writer.WriteStringValue("static");
            if (mods.HasFlag(TypeModifiers.Abstract)) writer.WriteStringValue("abstract");
            if (mods.HasFlag(TypeModifiers.Sealed)) writer.WriteStringValue("sealed");

            writer.WriteEndArray();
        }
    }
}
