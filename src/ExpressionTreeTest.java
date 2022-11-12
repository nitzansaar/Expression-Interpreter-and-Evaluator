import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
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
        List<Token> tokens = new LinkedList<>();
        Token t1 = new Token(Lexer.FLOAT, "3.14");
        Token t2 = new Token(Lexer.OPERATOR, "+");
        Token t3 = new Token(Lexer.INT, "7");
        tokens.add(t1);
        tokens.add(t2);
        tokens.add(t3);
        ExpressionTree.Node node1 = ExpressionTree.parseExpression(tokens);
        System.out.println(node1.toString());
        System.out.println(node1.right.toString());
        System.out.println(node1.left.toString());



    }


    @Test
    void evaluate() {
    }

    @Test
    void parseAssignment() {
        List<Token> tokens = new LinkedList<>();
        SymbolTable table = new SymbolTable();
        Token t1 = new Token(Lexer.IDENTIFER, "abc5");
        Token t2 = new Token(Lexer.ASSIGNMENT, "=");
        Token t3 = new Token(Lexer.INT, "7");
        tokens.add(t1);
        tokens.add(t2);
        tokens.add(t3);
        ExpressionTree.Node node1 = ExpressionTree.parseAssignment(tokens, table);
        System.out.println(table.getValue("abc5"));
    }
}