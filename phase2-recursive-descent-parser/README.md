# Phase 2: Recursive Descent Predictive Parser

This phase implements a recursive descent predictive parser in Java for validating the syntax of arithmetic expressions.

## Overview
The parser reads arithmetic expressions from `input.txt` and checks whether each expression follows a predefined context-free grammar (CFG). For each input line, the program prints either `Correct Syntax` or `Syntax Error` on the screen.

## Grammar
The parser is based on the following grammar:

- E  -> T E'
- E' -> + T E' | - T E' | ɛ
- T  -> F T'
- T' -> * F T' | / F T' | ɛ
- F  -> ( E ) | id

## Features
- Parses arithmetic expressions using recursive descent parsing
- Validates syntax according to CFG rules
- Supports `+`, `-`, `*`, and `/` operators
- Supports parentheses in nested expressions
- Prints `Correct Syntax` or `Syntax Error` for each input expression
- Processes multiple expressions from `input.txt`

## Files
- `RecursiveDescentParser.java` — main parser implementation
- `input.txt` — input file containing arithmetic expressions

## How It Works
1. The program reads one arithmetic expression per line from `input.txt`
2. Each expression must contain tokens separated by spaces
3. The parser applies the grammar rules using recursive descent functions
4. If the expression matches the grammar, the program prints `Correct Syntax`
5. Otherwise, it prints `Syntax Error`

## Example Input
```text
id * ( id + id ) $
( id + id ) * id $
( id * + id $
id + id * id $
