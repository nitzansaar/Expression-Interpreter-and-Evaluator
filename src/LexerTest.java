import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    Lexer luther;

    @BeforeEach
    void setup(){
        luther = new Lexer();
        luther.getInputFromString("#1234 + 5 ");
    }

    @org.junit.jupiter.api.Test
    void getNextToken() {
        luther.getNextToken(0);
        luther.getNextToken(1);
    }

    @Test
    void getAllTokens() {
        luther.getAllTokens();
    }
}