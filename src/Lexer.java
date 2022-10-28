import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public static final String INT="INT";
    public static final String FLOAT="FLOAT";
    public static final String IDENTIFER="ID";
    public static final String OPERATOR="OP";
    public static final String ASSIGNMENT="ASSIGN";
    public static final String EXPRASSIGNMENT="EXPR";
    public String buffer;
    public int ref = 0;

    public Lexer(String fileName) {

    }

    public Lexer() {
        buffer = "";
    }


    public void getInputFromFile(String fileName)  {

    }

    /**
     * Allows the user to provide a string as input.
     * You'll use this in the interpreter. It's also helpful for debugging.
     * @param s the String to be provided as input.
     */
    public void getInputFromString(String s) {
        buffer = s;
    }

    /**
     *  scan ahead in the string to the end of the current token.
        ref is the current pointer in the string.
     */
    public Token getNextToken(int ref) {
        return null;

    }

    public Token getAssignmentOperator(int ref) {
        return null;
    }



    public Token getOperator(int ref) {
        if (buffer.charAt(ref) == '+' || buffer.charAt(ref) == '-' || buffer.charAt(ref) == '*' ||
                buffer.charAt(ref) == '/') {
            return new Token(OPERATOR, buffer.substring(ref, ref+1));
        } else {
            throw new IllegalArgumentException("Lexical error: " + buffer.charAt(ref));
        }
    }

    public Token getIdentifier(int ref) {
        return null;
    }

    public Token getNumber(int ref) {
        int ptr = ref;
        boolean isFloat = false;
        while (ptr < buffer.length() &&
                (Character.isDigit(buffer.charAt(ref)) ||
                        buffer.charAt(ref) == '.')) {
            if (buffer.charAt(ref) == '.') {
                isFloat = true;
            }
            ptr++;
        }
        if (isFloat) {
            return new Token(FLOAT, buffer.substring(ref, ptr));
        } else {
            return new Token(INT, buffer.substring(ref, ptr));
        }
    }


    /* iterate through the buffer and return a list of tokens. */
    public List<Token> getAllTokens() {

    }



}



