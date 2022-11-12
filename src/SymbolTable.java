import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    Map<String, Double> symTable;
    Map<String, ExpressionTree> functionTable;

    /* you add constructors and setters/getters. */

    public SymbolTable(){
        symTable = new HashMap<>();
        functionTable = new HashMap<>();
    }
    public boolean contains(String s){
        return symTable.containsKey(s);
    }
    public Double getValue(String s){
        return symTable.get(s);
    }
    public void storeValue(String s, Double d){
        symTable.put(s, d);
    }
    public boolean containsFxn(String s){
        return functionTable.containsKey(s);
    }
    public ExpressionTree getFxn(String s){
        return functionTable.get(s);
    }
    public void storeFxn(String s, ExpressionTree tree){
        functionTable.put(s, tree);
    }

}
