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
        luther.getInputFromString("abc5 = 9");
        tokenList = luther.getAllTokens();
        ExpressionTree expressionTree = new ExpressionTree(tokenList.get(0));
        node = tree.parseIdentifier(tokenList.get(0));
        System.out.println(node.type);
    }
    @Test
    void parseExprOperator() {
        luther.getInputFromString("#");
        tokenList = luther.getAllTokens();
        node = tree.parseExprOperator(tokenList.get(0));
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
        luther.getInputFromString("=");
        tokenList = luther.getAllTokens();
        node = tree.parseAssignmentOp(tokenList.get(0));
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
    void parseAssignment() {
        List<Token> tokens = new LinkedList<>();
        SymbolTable table = new SymbolTable();
        Token t1 = new Token(Lexer.IDENTIFER, "abc5");
        Token t2 = new Token(Lexer.ASSIGNMENT, "=");
        Token t3 = new Token(Lexer.INT, "7");
        Token t4 = new Token(Lexer.OPERATOR, "+");
        Token t5 = new Token(Lexer.FLOAT, "4.65");
        tokens.add(t1);
        tokens.add(t2);
        tokens.add(t3);
        tokens.add(t4);
        tokens.add(t5);
        ExpressionTree.Node node1 = ExpressionTree.parseAssignment(tokens, table);
        System.out.println(table.getValue("abc5"));
    }

    @Test
    void parseTokens() {
        SymbolTable table = new SymbolTable();
        List<Token> tokens = new LinkedList<>();
        Token t1 = new Token(Lexer.IDENTIFER, "abc5");
        Token t2 = new Token(Lexer.ASSIGNMENT, "=");
        Token t3 = new Token(Lexer.INT, "7");
        tokens.add(t1);
        tokens.add(t2);
        tokens.add(t3);
        ExpressionTree.Node node1 = ExpressionTree.parseTokens(tokens, table);
        System.out.println(node1.type);


    }

    @Test
    void evaluate() {
        List<Token> tokens = new LinkedList<>();
        SymbolTable table = new SymbolTable();
        Token t1 = new Token(Lexer.IDENTIFER, "abc5");
        Token t2 = new Token(Lexer.ASSIGNMENT, "=");
        Token t3 = new Token(Lexer.INT, "9");
        Token t4 = new Token(Lexer.OPERATOR, "+");
        Token t5 = new Token(Lexer.FLOAT, "4.65");
        tokens.add(t1);
        tokens.add(t2);
        tokens.add(t3);
        tokens.add(t4);
        tokens.add(t5);
        table.storeValue(t1.value, Double.valueOf(t3.value));
        ExpressionTree tree1 = new ExpressionTree(t1);
        ExpressionTree expressionTree = new ExpressionTree(t1, tree1, tree1);
        double result = expressionTree.evaluate(table);
        System.out.println(result);
    }
}