# Java Lexical Analyzer

A lexical analyzer implemented in Java for a Compiler Construction project.

## Overview
This project simulates the lexical analysis phase of a compiler. It reads source code from an input file, identifies lexemes, classifies them into tokens, and writes the results to an output file. The analyzer is designed to process common programming language elements such as identifiers, reserved words, literals, operators, comments, and punctuation symbols.

## Features
- Identifies reserved words and user-defined identifiers
- Recognizes integer and floating-point literals
- Supports arithmetic operators and arithmetic assignment operators
- Supports relational and logical operators
- Handles increment and decrement operators
- Ignores whitespace and comments during tokenization
- Recognizes string literals and character literals
- Identifies punctuation symbols such as parentheses, brackets, braces, commas, and semicolons
- Reports unrecognized or invalid tokens as errors

## Technologies Used
- Java
- File I/O
- PushbackReader
- BufferedWriter
- Finite Automata concepts 

## Project Structure
- `LexicalAnalyzer.java` — main source code of the lexical analyzer
- `input.txt` — input file containing source code to be analyzed
- `output.txt` — output file containing identified lexemes and tokens
- `docs/` — optional folder for diagrams or supporting material such as the integrated finite automata design

## How It Works
1. The user writes source code inside `input.txt`
2. The program reads the file character by character
3. It classifies lexemes into their corresponding token types
4. The identified lexemes and tokens are written to `output.txt`
5. If `input.txt` is missing, the program prints an error message and stops execution

## Supported Token Categories
The lexical analyzer is designed to recognize:
- Reserved words
- Identifiers
- Integer literals
- Floating-point literals
- Arithmetic operators
- Assignment operators
- Relational operators
- Logical operators
- Increment and decrement operators
- Single-line and multi-line comments
- Character literals
- String literals
- Punctuation marks and delimiters 
