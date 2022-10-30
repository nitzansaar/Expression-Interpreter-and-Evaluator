import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeTest {
    ExpressionTree tree;
    Lexer luther = new Lexer();
    List<Token> tokenList = new ArrayList<>();
    ExpressionTree.Node node;
    

    @BeforeEach
    void setup(){
        luther.getInputFromString("123 + 8 - 9 = abc");
        tokenList = luther.getAllTokens();
        tree = new ExpressionTree(tokenList.get(0));

    }

    @Test
    void parseNumber() {
        node = tree.parseNumber(tokenList.get(0));
        System.out.println(node);
    }

    @Test
    void parseIdentifier() {
        node = tree.parseIdentifier(tokenList.get(6));
        System.out.println(node);
    }
}