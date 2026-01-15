using System;

namespace CSharpAnalyzer.Model;

[Flags]
public enum MethodModifiers
{
    None = 0,
    Static = 1 << 0,
    Abstract = 1 << 1,
    Virtual = 1 << 2,
    Override = 1 << 3,
    Sealed = 1 << 4,
    Async = 1 << 5
}
