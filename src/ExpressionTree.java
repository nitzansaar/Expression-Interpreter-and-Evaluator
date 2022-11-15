/*
    grammar:
    EXPR -> NUMBER
    EXPR -> IDENTIFIER
    EXPR -> EXPR OP EXPR
    ASSIGNMENT -> IDENTIFIER ASSIGNOP EXPR
    EXPRASSIGNMENT -> IDENTIFIER EXPRASSIGN EXPR
    OP -> + | - | * | /
    NUMBER -> INTEGER
    NUMBER -> FLOAT
    INTEGER -> [0-9]+
    FLOAT -> [0-9]+.[0-9]+
    IDENTIFIER -> [A-Za-z]+[A-Za-z0-9]*
    ASSIGNOP -> =
    EXPRASSIGN -> #


 */

import java.util.*;


public class ExpressionTree {

    static Node root;
    static HashMap<String, Integer> precedence;
    static {
        precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
    }

    public ExpressionTree() {
        root = null;
    }

    public ExpressionTree(Token t, ExpressionTree leftTree, ExpressionTree rightTree) {
        this.root = new Node(t);
        this.root.left = leftTree.root;
        this.root.right = rightTree.root;
    }

    public ExpressionTree(Token t) {
        this.root = new Node(t);
        this.root.left = null;
        this.root.right = null;
    }

    public static class Node extends Token {
        String type;
        String val;
        Node left;
        Node right;

        Node() {
            type = "";
            val = "";
            left = null;
            right = null;
        }

        Node(Token t) {
            this.type = t.type;
            this.val = t.value;
        }

        public String toString() {
            return this.type + ":" + this.val;
        }

        /* evaluate an Expression tree via postorder traversal. */
        public double eval(SymbolTable table) {
            if(this.type == Lexer.FLOAT || this.type == Lexer.INT){
                return Double.valueOf(this.val);
            }else if(this.type == Lexer.IDENTIFER) {
                if (table.contains(this.val)) {
                    return table.getValue(this.val);
                } else if (table.hasFxn(this.val)) {
                    return table.getFxn(this.val).evaluate(table);
                }else {
                    throw new IllegalArgumentException("Not in symbol table");
                }
            }else if(this.type == Lexer.OPERATOR){
                double l = left.eval(table);
                double r = right.eval(table);
                if(this.val.equals("+")){
                    return l + r;
                }else if(this.val.equals("-")){
                    return l - r;
                }else if(this.val.equals("*")){
                    return l * r;
                }else if(this.val.equals("/")){
                    return l / r;
                } else{
                    System.out.println("Error: Unknown operator");
                    return 0.0;
                }
            }else{
                throw new IllegalArgumentException("Error");
            }
        }


    }

/*parse variable identifiers */
    public static Node parseIdentifier(Token t) {
        if (t == null) {
            return new Node();
        } else if (t.type.equals(Lexer.IDENTIFER)) {
            return new Node(t);
        } else {
            throw new IllegalArgumentException("Parse error: " + t);
        }
    }

    /* parse = */
    public static Node parseAssignmentOp(Token t) {
        if (t == null) {
            return new Node();
        } else if (t.type.equals(Lexer.ASSIGNMENT)) {
            return new Node(t);
        } else {
            throw new IllegalArgumentException("Parse error: " + t);
        }
    }
    /* parse +,-,*,/ */
    public static Node parseOperator(Token t) {
        if (t == null) {
            return new Node();
        } else if (t.type.equals(Lexer.OPERATOR)) {
            return new Node(t);
        } else {
            throw new IllegalArgumentException("Parse error: " + t);
        }
    }

    /* parse the expr (#) operator */
    public static Node parseExprOperator(Token t) {
        if (t == null) {
            return new Node();
        } else if (t.type.equals(Lexer.EXPRASSIGNMENT)) {
            return new Node(t);
        } else {
            throw new IllegalArgumentException("Parse error: " + t);
        }
    }

    /* parse a number  and return the appropriate node */

    public static Node parseNumber(Token t) {
        if (t == null) {
            return new Node();
        } else if (t.type.equals(Lexer.INT) || t.type.equals(Lexer.FLOAT)) {
            return new Node(t);
        } else {
            throw new IllegalArgumentException("Parse error: " + t);
        }

    }

    public static Node parseNumberOrID(Token t){
        if(t == null){
            return new Node();
        }else if(t.type.equals(Lexer.INT) || t.type.equals(Lexer.FLOAT)||
                    t.type.equals(Lexer.IDENTIFER)){
            return new Node(t);
        }else {
            throw new IllegalArgumentException("Parse error: " + t);
        }
    }

    /* a helper method to tell which of two operators has precendence. */
    public static boolean hasPrecedence(String op1, String op2) {
        if (precedence.get(op1) > precedence.get(op2)) {
            return true;
        } else {
            return false;
        }
    }

    /* parseExpression. Use the shunting algorithm to parse the list of tokens into an expression tree. */
    public static Node parseExpression(List<Token> tokenList) {
        Stack<Node> operands = new Stack<>();
        Stack<Node> operators = new Stack<>();

        for(Token t : tokenList){
            if(t.type.equals(Lexer.IDENTIFER) ||t.type.equals(Lexer.INT) || t.type.equals(Lexer.FLOAT)){
                operands.push(new Node(t));
            }else if(t.type.equals(Lexer.OPERATOR)){
                if(operators.isEmpty() || hasPrecedence(t.value, operators.peek().val)){
                    operators.push(new Node(t));
                }else{
                    if(operands.size() < 2){
                        throw new IllegalArgumentException("Parse Error " + t);
                    }
                    Node tree = operators.pop();
                    tree.right = operands.pop();
                    tree.left = operands.pop();

                    operands.push(tree);
                    operators.push(new Node(t));
                }
            }else{
                throw new IllegalArgumentException("Parse Error " + t);
            }
        }
        while(!operators.isEmpty()){
            Node temp = operators.pop();
            if(operands.size() < 2){
                throw new IllegalArgumentException("Parse error " + temp);
            }
            temp.right = operands.pop();
            temp.left = operands.pop();
            operands.push(temp);
        }
        if(operands.size() != 1){
            throw new IllegalArgumentException("Parse error");
        }
        return operands.pop();
    }

    /* parse an assignment statement - grab the variable and assignment operator, parse the expression on the right-hand side,
        evaluate it, and store the result in the symbol table.
     */
    public static Node parseAssignment(List<Token> tokenList, SymbolTable table) {
        if(tokenList.isEmpty() || tokenList.size() == 1) {
            return null;
        }else{
            Node node = parseIdentifier(tokenList.get(0));
            Node node1 = parseAssignmentOp(tokenList.get(1));
            if(node == null || node1 == null){
                throw new IllegalArgumentException("Parse error");
            }else{
                Node right = parseExpression(tokenList.subList(2,tokenList.size()));
                double d = right.eval(table);
                table.storeValue(node.val, d);
                return right;
            }
        }
    }

    /* Similar to parseAssignment, except that we're not going to evaluate the expression. Instead, store the expression tree
        in the symbol table.
     */

    public static Node parseExprAssignment(List<Token> tokenList, SymbolTable table) {
        if(tokenList.isEmpty() || tokenList.size() == 1){
            return null;
        }else{
            Node node = parseIdentifier(tokenList.get(0));
            Node node1 = parseExprOperator(tokenList.get(1));
            if(node == null || node1 == null){
                throw new IllegalArgumentException("Parse error");
            }else{
                Node r = parseExpression(tokenList.subList(2, tokenList.size()));
                ExpressionTree expressionTree = new ExpressionTree();
                expressionTree.root = r;
                table.storeFxn(node.val, expressionTree);
                return r;
            }
        }
    }

    /* take a list of tokens, look ahead to see what we are parsing,  and call the appropriate method */

    public static Node parseTokens(List<Token> tokenList, SymbolTable table) {
        Node node;
        if(tokenList.size() == 1){
            if(tokenList.get(0).type.equals(Lexer.INT) ||
                    tokenList.get(0).type.equals(Lexer.FLOAT) ||
                    tokenList.get(0).type.equals(Lexer.IDENTIFER)){
                node = parseNumberOrID(tokenList.get(0));
            }else{
                throw new IllegalArgumentException("Error");
            }
        }else if(tokenList.get(0).type.equals(Lexer.IDENTIFER) &&
                tokenList.get(1).type.equals(Lexer.ASSIGNMENT)){
            node = parseAssignment(tokenList, table);
        }else if(tokenList.get(0).type.equals(Lexer.IDENTIFER) &&
                tokenList.get(1).type.equals(Lexer.EXPRASSIGNMENT)){
            node = parseExprAssignment(tokenList, table);
        }else{
            node = parseExpression(tokenList);
        }
        if(node == null){
            System.out.println("Error");
        }
        return node;
    }

    /* wrapper method for parseTokens */
    public void parse(List<Token> tokenList, SymbolTable table) {
        root = parseTokens(tokenList, table);
    }

    /* wrapper method for eval */
    public static double evaluate(SymbolTable table) {
        return root.eval(table);
    }

}
