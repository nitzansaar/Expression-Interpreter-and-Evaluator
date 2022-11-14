import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    Token token;
    Token token1;

    Lexer luther;

    @BeforeEach
    void setup(){
        luther = new Lexer();
        luther.getInputFromString("abc5 + 123 + zyx = 19#");
    }

    @org.junit.jupiter.api.Test
    void getNextToken() {
        token = luther.getNextToken(0);
        token1 = luther.getNextToken(5);
        System.out.println(token);
        System.out.println(token1);
    }

    @Test
    void getAllTokens() {
        List<Token> tokenList = new ArrayList<>();
        tokenList = luther.getAllTokens();
        System.out.println(tokenList.toString());
    }

    @Test
    void getExpAssignment() {
        luther.getInputFromString("#");
        token = luther.getExpAssignment(0);
        System.out.println(token.toString());
    }

    @Test
    void getAssignmentOperator() {
        luther.getInputFromString("1 = x");
        token = luther.getAssignmentOperator(2);
        System.out.println(token.toString());
    }

    @Test
    void getNumber() {
        luther.getInputFromString("1 + 3");
        token = luther.getNumber(0);
        System.out.println(token);
        luther.getInputFromString("1.456");
        token = luther.getNumber(0);
        System.out.println(token);
    }

    @Test
    void isIntOrFloat() {
        assertTrue(luther.isIntOrFloat('1'));
        assertFalse(luther.isIntOrFloat('b'));
    }

    @Test
    void isIdentifier() {
        assertTrue(luther.isIdentifier('a'));
        assertFalse(luther.isIdentifier('1'));
    }

    @Test
    void getOperator() {
        luther.getInputFromString("-");
        token = luther.getOperator(0);
        System.out.println(token);
        luther.getInputFromString("+");
        token = luther.getOperator(0);
        System.out.println(token);
        luther.getInputFromString("/");
        token = luther.getOperator(0);
        System.out.println(token);
        luther.getInputFromString("*");
        token = luther.getOperator(0);
        System.out.println(token);

    }

    @Test
    void getIdentifier() {
        luther.getInputFromString("abc123 + 7");
        Token s = luther.getIdentifier(0);
        System.out.println(s);
    }
}