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

    public boolean isOperator(char c){
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }
    public Token getNextToken(int ref) {
        Token token = new Token();
        if (isOperator(buffer.charAt(ref))) {
            token = getOperator(ref);
        }else if(Character.isDigit(buffer.charAt(ref)) || (buffer.charAt(ref) == '.')){
            token = getNumber(ref);
        }else if(buffer.charAt(ref) == '='){
            token = getAssignmentOperator(ref);
        }else if(Character.isAlphabetic(buffer.charAt(ref))){
            token = getIdentifier(ref);
        }else if(buffer.charAt(ref) == '#'){
            token = getExpAssignment(ref);
        }
        return token;

    }

    public Token getExpAssignment(int ref){
        if(buffer.charAt(ref) == '#'){
            return new Token(EXPRASSIGNMENT, buffer.substring(ref, ref+1));
        }else {
            throw new IllegalArgumentException("Lexical error: " + buffer.charAt(ref));
        }
    }

    public Token getAssignmentOperator(int ref) {
        if(buffer.charAt(ref) == '='){
            return new Token(ASSIGNMENT, buffer.substring(ref, ref+1));
        }else {
            throw new IllegalArgumentException("Lexical error: " + buffer.charAt(ref));
        }
    }
    public Token getOperator(int ref) {
        if (buffer.charAt(ref) == '+' || buffer.charAt(ref) == '-' || buffer.charAt(ref) == '*' ||
                buffer.charAt(ref) == '/') {
            return new Token(OPERATOR, buffer.substring(ref, ref+1));
        } else {
            throw new IllegalArgumentException("Lexical error: " + buffer.charAt(ref));
        }
    }
    public boolean isIdentifier(char c){
        return Character.isAlphabetic(c);
    }


    public Token getIdentifier(int ref) {
        int ptr = ref + 1;
        boolean whitespace = false;
        if(Character.isAlphabetic(buffer.charAt(ref))){
            while(!whitespace && ptr < buffer.length()){
                if(buffer.charAt(ptr) == ' '){
                    whitespace = true;
                }
                ptr++;
            }
            return new Token(IDENTIFER, buffer.substring(ref, ptr));
        }else {
            throw new IllegalArgumentException("Lexical error: " + buffer.charAt(ref));
        }
    }

    public boolean isIntOrFloat(char c){
        return (Character.isDigit(c) || c == '.');
    }

    public Token getNumber(int ref) {
        int ptr = ref;
        boolean isFloat = false;
        boolean whitespace = false;
        while (ptr < buffer.length() && (Character.isDigit(buffer.charAt(ref)) || buffer.charAt(ref) == '.') && !whitespace) {
            if (buffer.charAt(ptr) == '.') {
                isFloat = true;
            }
            if(buffer.charAt(ptr) == ' '){
                whitespace = true;
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
        List<Token> tokenList = new ArrayList<>();
        Token temp;
        int ref = 0;
        System.out.println(buffer.length());
        while(ref < buffer.length()){
            if(buffer.charAt(ref) != ' ') {
                System.out.println(ref);
                temp = getNextToken(ref);
                tokenList.add(temp);
                System.out.println(temp.toString());
            }
            if(isIntOrFloat(buffer.charAt(ref))){// edge case
                while(isIntOrFloat(buffer.charAt(ref+1))){
                    ref++;
                    //System.out.println(ref);
                }
            }else if(isIdentifier(buffer.charAt(ref))){// edge case
                while(isIntOrFloat(buffer.charAt(ref+1)) || isIdentifier(buffer.charAt(ref+ 1))){
                    ref++;
                    System.out.println(ref);
                }
            }
            ref++;
        }
        return tokenList;
    }



}



