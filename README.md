# Interpreter Project

The Interpreter project is a simple interactive interpreter that can parse and evaluate mathematical expressions, perform assignments, and store variables. It supports basic arithmetic operations, variable assignments, and expression tree evaluations.

## Overview

The project consists of the following key components:

- **Interpreter:** The main class responsible for launching the interactive interpreter and executing user input or file input. It interacts with the Lexer and ExpressionTree classes to tokenize and evaluate expressions.

- **Lexer:** This class performs lexical analysis by tokenizing input strings into a list of tokens. Tokens can represent integers, floats, identifiers, operators, assignments, and expression assignment operators.

- **ExpressionTree:** This class constructs and evaluates expression trees based on the provided tokens. It handles arithmetic operations, variable lookups, and function evaluations. The parse methods implement the shunting algorithm to convert infix expressions into expression trees.

- **SymbolTable:** A data structure that stores variables and function expressions. It provides methods to store, retrieve, and evaluate variables and functions.

## Getting Started

1. Clone or download the repository.
2. Make sure you have Java installed on your machine.
3. Compile the source code using `javac *.java`.
4. Run the interpreter using `java Interpreter`.

## Usage

The Interpreter allows you to interactively input expressions and see the results. You can also input expressions from a file.

## What I Learned

Through this project, I gained practical experience in the following areas:

- **Lexical Analysis:** I learned how to tokenize input strings and categorize tokens using the Lexer class. This allowed me to prepare input for parsing and evaluation.

- **Expression Parsing:** The ExpressionTree class taught me about parsing arithmetic expressions and converting them into expression trees using the shunting algorithm. I learned how to handle operator precedence and build expression trees from tokens.

- **Symbol Management:** The SymbolTable class demonstrated the importance of managing variables and functions efficiently. I learned how to store, retrieve, and evaluate these symbols within an interpreter.

- **Interactive User Input:** I explored how to create an interactive interpreter that reads input from the user and provides real-time feedback on expressions and results.

- **Testing with JUnit:** I practiced writing unit tests using JUnit to verify the functionality of components like the ExpressionTree class. This helped ensure that my code works as expected.

## Example Usage

```java
Interpreter shell = new Interpreter();
shell.runShell();
