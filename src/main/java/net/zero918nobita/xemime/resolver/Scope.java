package net.zero918nobita.xemime.resolver;

import net.zero918nobita.xemime.ast.Symbol;
import net.zero918nobita.xemime.type.AnyType;
import net.zero918nobita.xemime.type.Type;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 意味解析中にインスタンスが生成される、スコープを表すクラスです。
 * @author Kodai Matsumoto
 */

public class Scope {
    private static int maxScopeID = 0;

    private int scopeID;
    private Scope parent;
    private HashMap<Symbol, Variable> variables;
    private ArrayList<Symbol> postponedSymbols = new ArrayList<>();

    public Scope(Scope parent) {
        this.parent = parent;
        variables = new HashMap<>();
        scopeID = maxScopeID;
        maxScopeID ++;
    }

    @Override
    public int hashCode() {
        return scopeID;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Scope<#");
        output.append(scopeID);
        output.append(">{");
        int i = 0;
        for (Symbol sym : variables.keySet()) {
            output.append(variables.get(sym).getType());
            output.append(" ");
            output.append(sym);
            i ++;
            if (i < variables.size()) output.append(", ");
        }
        return output.append("}").toString();
    }

    public Scope parent() {
        return parent;
    }

    void defVar(Type type, Symbol sym) {
        variables.put(sym, new Variable(type));
    }

    public boolean referVar(int location, Symbol sym) throws SemanticError {
        if (variables.containsKey(sym)) return true;
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.containsKey(sym)) return true;
        sym.setLocation(location);
        postponedSymbols.add(sym);
        return false;
    }

    Type getTypeOfVariable(Symbol sym) throws SemanticError {
        if (variables.containsKey(sym)) return variables.get(sym).getType();
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.containsKey(sym)) return s.variables.get(sym).getType();
        // throw new SemanticError(sym.getLocation(), 22, sym);
        // 未定義シンボルとして保留する
        postponedSymbols.add(sym);
        return new AnyType();
    }

    boolean hasVariable(Symbol sym) {
        if (variables.containsKey(sym)) return true;
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.containsKey(sym)) return true;
        return false;
    }

    ArrayList<Symbol> getPostponedSymbols() {
        return postponedSymbols;
    }
}
