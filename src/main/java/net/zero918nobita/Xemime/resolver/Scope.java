package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;

/**
 * 意味解析中にインスタンスが生成される、スコープを表すクラスです。
 * @author Kodai Matsumoto
 */

public class Scope {
    private static int maxScopeID = 0;

    private int scopeID;
    private Scope parent;
    private ArrayList<Symbol> variables;

    public Scope(Scope parent) {
        this.parent = parent;
        variables = new ArrayList<>();
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
        if (variables.size() >= 1) output.append(variables.get(0));
        for (int i = 1; i < variables.size(); i++) {
            output.append(", ");
            output.append(variables.get(i));
        }
        return output.append("}").toString();
    }

    public Scope parent() {
        return parent;
    }

    public void defVar(Symbol sym) {
        variables.add(sym);
    }

    public void referVar(int location, Symbol sym) throws Exception {
        if (variables.contains(sym)) return;
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.contains(sym)) return;
        throw new Exception(location + ": シンボル `" + sym + "` を解決できません");
    }
}
