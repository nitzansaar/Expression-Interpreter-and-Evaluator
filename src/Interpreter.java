import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
    Lexer luthor;
    SymbolTable variables;


    public Interpreter() {
        luthor = new Lexer();
        variables = new SymbolTable();
    }

    /* a method that can launch an interactive interpreter.
        Read in a line, execute it, and print out the result.
        Also add a verbose option that prints out the input and symbol table.
     */
    public void runShell() {
        Scanner scanner = new Scanner(System.in);
        List<Token> tokenList;
        System.out.println("Enter \"STOP\" to exit and view Symbol Table");
            while (true) {
                try {
                    System.out.print(">>");
                    String temp = scanner.nextLine();
                    if(temp.equalsIgnoreCase("STOP")){
                        return;
                    }
                    System.out.println("Input: " + temp);
                    luthor.getInputFromString(temp);
                    tokenList = luthor.getAllTokens();
                    ExpressionTree tree = new ExpressionTree();
                    tree.parse(tokenList, variables);
                    System.out.println("Output " + tree.evaluate(variables));
                } catch (Exception e) {
                    System.out.println("Invalid Input");
                }
            }
    }

    /* a method that can read a series of lines in from a file, execute each one,
    and print out the result.
        Also add a verbose option that prints out the input and symbol table.

     */
    public void executeFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner input = new Scanner(file);
        List<Token> tokenList;
        while(input.hasNextLine()){
            try {
                String temp = input.nextLine();
                System.out.print(">>");
                luthor.getInputFromString(temp);
                tokenList = luthor.getAllTokens();
                ExpressionTree tree = new ExpressionTree();
                tree.parse(tokenList, variables);
                System.out.println(tree.evaluate(variables));
                System.out.println("Input: " + temp);
                System.out.println("Output " + tree.evaluate(variables));
            }catch (Exception e){
                System.out.println("Error");
            }
        }
    }

    public static void main (String[] args) throws FileNotFoundException {
        Interpreter shell = new Interpreter();
        shell.runShell();
        //shell.executeFile("testfile.txt");
        System.out.println("Symbol table: ");
        shell.variables.printElements();
    }

}
