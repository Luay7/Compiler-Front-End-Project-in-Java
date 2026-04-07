import java.io.*;
import java.util.*;

public class LexicalAnalyzer {
    private static final Set<String> RESERVED_WORDS = new HashSet<>(Arrays.asList(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
        "class", "const", "continue", "default", "do", "double", "else", "enum",
        "extends", "final", "finally", "float", "for", "goto", "if", "implements",
        "import", "instanceof", "int", "interface", "long", "main", "native", "new",
        "null", "package", "private", "protected", "public", "return", "short",
        "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
        "throws", "transient", "try", "void", "volatile", "while"
    ));

    private static PushbackReader reader;
    private static BufferedWriter writer;
    private static StringBuilder lexeme = new StringBuilder();

    public static void main(String[] args) {
        try {
            reader = new PushbackReader(new FileReader("input.txt"));
            writer = new BufferedWriter(new FileWriter("output.txt"));
            
            writer.write("Lexemes\t\tTokens\n");

            tokenizer();

            writer.close();
            System.out.println("Tokens have been identified successfully....!!!");
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private static void tokenizer() throws IOException {
        int character;

        while ((character = reader.read()) != -1) {
            char lookahead = (char) character;

            if (Character.isWhitespace(lookahead)) {
                continue;
            }

            lexeme.setLength(0);
            lexeme.append(lookahead);
            
            if (Character.isLetter(lookahead) || lookahead == '_') {
                readIdentifier();
            } else if (Character.isDigit(lookahead)) {
                readNumber();
            } else {
                readSpecialCharacter(lookahead);
            }
        }
    }

    private static void readIdentifier() throws IOException {
        int character;
        while (true) {
            character = reader.read();
            if (character == -1) {
                break;
            }

            char lookahead = (char) character;
            if (Character.isLetterOrDigit(lookahead) || lookahead == '_') {
                lexeme.append(lookahead);
            } else {
                reader.unread(character);
                break;
            }
        }

        String word = lexeme.toString();
        if (RESERVED_WORDS.contains(word)) {
            writer.write(word + "\t\t" + word + "\n");
        } else {
            writer.write(word + "\t\tid\n");
        }
    }

    private static void readNumber() throws IOException {
        int character;
        boolean isFloat = false;
        
        while (true) {
            character = reader.read();
            if (character == -1) {
                break;
            }

            char lookahead = (char) character;
            if (Character.isDigit(lookahead)) {
                lexeme.append(lookahead);
            } else if (lookahead == '.') {
                isFloat = true;
                lexeme.append(lookahead);
            } else {
                reader.unread(character);
                break;
            }
        }
        
        if (isFloat) {
            writer.write(lexeme.toString() + "\t\tFloat_Literal\n");
        } else {
            writer.write(lexeme.toString() + "\t\tInt_Literal\n");
        }
    }

    private static void readSpecialCharacter(char lookahead) throws IOException {
        switch (lookahead) {
            // Arithmetic Operators
            case '+':
                if (checkNextCharacter('+')) {
                    writer.write("++\t\tInc_Op\n");
                } else if (checkNextCharacter('=')) {
                    writer.write("+=\t\tAdd_Assign_Op\n");
                } else {
                    writer.write("+\t\tPlus\n");
                }
                break;
            case '-':
                if (checkNextCharacter('-')) {
                    writer.write("--\t\tDec_Op\n");
                } else if (checkNextCharacter('=')) {
                    writer.write("-=\t\tSub_Assign_Op\n");
                } else {
                    writer.write("-\t\tMinus\n");
                }
                break;
            case '*':
                if (checkNextCharacter('=')) {
                    writer.write("*=\t\tMul_Assign_Op\n");
                } else {
                    writer.write("*\t\tMultiply\n");
                }
                break;
            case '/':
                if (checkNextCharacter('=')) {
                    writer.write("/=\t\tDiv_Assign_Op\n");
                } else {
                    // Check for comments
                    if (checkNextCharacter('/')) {
                        readSingleLineComment();
                    } else if (checkNextCharacter('*')) {
                        readMultiLineComment();
                    } else {
                        writer.write("/\t\tDivide\n");
                    }
                }
                break;
            case '%':
                if (checkNextCharacter('=')) {
                    writer.write("%=\t\tMod_Assign_Op\n");
                } else {
                    writer.write("%\t\tModulus\n");
                }
                break;
            case '^':
                writer.write("^\t\tExp\n");
                break;
            // Relational Operators
            case '<':
                if (checkNextCharacter('=')) {
                    writer.write("<=\t\tLess_Equal\n");
                } else {
                    writer.write("<\t\tLess\n");
                }
                break;
            case '>':
                if (checkNextCharacter('=')) {
                    writer.write(">=\t\tGreater_Equal\n");
                } else {
                    writer.write(">\t\tGreater\n");
                }
                break;
            case '=':
                if (checkNextCharacter('=')) {
                    writer.write("==\t\tEq_Op\n");
                } else {
                    writer.write("=\t\tAssign_Op\n");
                }
                break;
            case '!':
                if (checkNextCharacter('=')) {
                    writer.write("!=\t\tNot_Equal\n");
                } else {
                    writer.write("!\t\tNot\n");
                }
                break;
            // Logical Operators
            case '&':
                if (checkNextCharacter('&')) {
                    writer.write("&&\t\tLogical_And\n");
                } else {
                    reportError("Unrecognized token: " + lookahead);
                }
                break;
            case '|':
                if (checkNextCharacter('|')) {
                    writer.write("||\t\tLogical_Or\n");
                } else {
                    reportError("Unrecognized token: " + lookahead);
                }
                break;
            // Punctuation Marks
            case ';':
                writer.write(";\t\tSemi_Colon\n");
                break;
            case ',':
                writer.write(",\t\tComma\n");
                break;
            case '.':
                writer.write(".\t\tDot\n");
                break;
            case '?':
                writer.write("?\t\tQuestion_mark\n");
                break;
            case '{':
                writer.write("{\t\tLeft_Curly\n");
                break;
            case '}':
                writer.write("}\t\tRight_Curly\n");
                break;
            case '(':
                writer.write("(\t\tLeft_Paren\n");
                break;
            case ')':
                writer.write(")\t\tRight_Paren\n");
                break;
            case '[':
                writer.write("[\t\tLeft_Square_Bracket\n");
                break;
            case ']':
                writer.write("]\t\tRight_Square_Bracket\n");
                break;
            // String Literals
            case '\"':
                readStringLiteral();
                break;
            // Character Literals
            case '\'':
                readCharLiteral();
                break;
            default:
                reportError("Unrecognized token: " + lookahead);
                break;
        }
    }

    private static void readSingleLineComment() throws IOException {
        int character;
        while ((character = reader.read()) != -1) {
            char lookahead = (char) character;
            if (lookahead == '\n') {
                break;
            }
        }
    }

    private static void readMultiLineComment() throws IOException {
        int character;
        while ((character = reader.read()) != -1) {
            char lookahead = (char) character;
            lexeme.append(lookahead);
            if (lookahead == '*' && checkNextCharacter('/')) {
                break;
            }
        }
    }

    private static void readStringLiteral() throws IOException {
        int character;
        lexeme.setLength(0); // Clear lexeme for string
        lexeme.append('\"');
        
        while ((character = reader.read()) != -1) {
            char lookahead = (char) character;
            lexeme.append(lookahead);
            if (lookahead == '\"') {
                break;
            }
        }
        writer.write(lexeme.toString() + "\t\tString_Literal\n");
    }

    private static void readCharLiteral() throws IOException {
        int character;
        lexeme.setLength(0); // Clear lexeme for char
        lexeme.append('\'');
        
        character = reader.read();
        if (character != -1) {
            lexeme.append((char) character);
            character = reader.read();
            if (character != -1 && (char) character == '\'') {
                lexeme.append('\'');
                writer.write(lexeme.toString() + "\t\tChar_Literal\n");
            } else {
                reportError("Unmatched character literal: " + lexeme.toString());
            }
        }
    }

    private static boolean checkNextCharacter(char expected) throws IOException {
        int character = reader.read();
        if (character == expected) {
            return true;
        } else {
            if (character != -1) {
                reader.unread(character);
            }
            return false;
        }
    }

    private static void reportError(String message) throws IOException {
        writer.write("Error: " + message + "\n");
        System.out.println("Error: " + message);
    }
}
