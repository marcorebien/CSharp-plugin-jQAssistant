using System;

namespace CSharpAnalyzer.Model;

[Flags]
public enum TypeModifiers
{
    None = 0,
    Static = 1 << 0,
    Abstract = 1 << 1,
    Sealed = 1 << 2
}
