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
        luther.getInputFromString("3 + 4 * 5 - 6");
        tokenList = luther.getAllTokens();
        tree = new ExpressionTree(tokenList.get(0));

    }
    @Test
    void parseNumber() {
        node = tree.parseNumber(tokenList.get(0));
        assertTrue(node.type.equals("INT"));
        System.out.println(node);
    }
    @Test
    void parseIdentifier() {
        node = tree.parseIdentifier(tokenList.get(6));
        assertTrue(node.type.equals("ID"));
        System.out.println(node);
    }
    @Test
    void parseExprOperator() {
        node = tree.parseExprOperator(tokenList.get(7));
        assertTrue(node.type.equals("EXPR"));
        System.out.println(node);
    }
    @Test
    void parseOperator() {
        node = tree.parseOperator(tokenList.get(1));
        assertTrue(node.type.equals("OP"));
        System.out.println(node);
    }

    @Test
    void parseAssignmentOp() {
        node = tree.parseAssignmentOp(tokenList.get(5));
        assertTrue(node.type.equals("ASSIGN"));
        System.out.println(node);
    }

    @Test
    void parseExpression() {
        ExpressionTree.Node n = tree.parseExpression(tokenList);
    }
}