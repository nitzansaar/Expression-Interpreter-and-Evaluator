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
        luther.getInputFromString("abc123 = 9 ");
    }

    @org.junit.jupiter.api.Test
    void getNextToken() {
        token = luther.getNextToken(0);
        token1 = luther.getNextToken(1);
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
        token = luther.getExpAssignment(0);
        System.out.println(token.toString());
    }

    @Test
    void getAssignmentOperator() {
        token = luther.getAssignmentOperator(10);
        System.out.println(token.toString());
    }

    @Test
    void getNumber() {
        token = luther.getNumber(3);
        System.out.println(token);
    }
}