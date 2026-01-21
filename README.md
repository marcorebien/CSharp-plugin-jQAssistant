# CSharp-plugin-jQAssistant
A Repo for my Bachelor Thesis and a CSharp Plugin for the jQAssistant


jQAssistant C# Plugin (Roslyn-based)

This plugin analyzes C# projects/solutions using a Roslyn-based analyzer (C# CLI tool) and imports the exported JSON into the jQAssistant graph.

Requirements
Java side

Java (tested with Java 17+; project currently uses JDK 23 in development)

Maven

jQAssistant Maven Plugin (used via mvn jqassistant:scan)

C# side (Analyzer)

.NET SDK installed (so dotnet is available on PATH)

The C# analyzer project is present in this repository (e.g. csharp/src/CSharpAnalyzer/CSharpAnalyzer.csproj)

clone the Repo install with 

$repo = Join-Path $env:USERPROFILE ".jqassistant\repository" mvn -f <Path-to-repo> clean install "-Dmaven.repo.local=$repo" 
Download jQAssistant cli distribution 
https://production.portal.central.sonatype.com/artifact/com.buschmais.jqassistant.cli/jqassistant-commandline-neo4jv5/2.6.0/versions 

start scan with .\bin\jqassistant scan -f <path to C# project .csproj or .sln> 
start server with .\bin\jqassistant server