package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.type.Type;

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
        output.append("> {");
        for (int i = 0; i < variables.size(); i++) {
            output.append(", ");
            output.append(variables.get(i));
        }
        int i = 0;
        for (Symbol sym : variables.keySet()) {
            output.append("[");
            output.append(variables.get(sym).getType());
            output.append("]");
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

    public void referVar(int location, Symbol sym) throws Exception {
        if (variables.containsKey(sym)) return;
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.containsKey(sym)) return;

        // SemanticError - シンボルの参照の解決に失敗しました
        throw new SemanticError(location, 2, sym);
    }

    Type getTypeOfVariable(Symbol sym) throws SemanticError {
        if (variables.containsKey(sym)) return variables.get(sym).getType();
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.containsKey(sym)) return s.variables.get(sym).getType();
        throw new SemanticError(sym.getLocation(), 22, sym);
    }

    boolean hasVariable(Symbol sym) {
        if (variables.containsKey(sym)) return true;
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.containsKey(sym)) return true;
        return false;
    }
}
