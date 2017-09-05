package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.X_Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * 意味解析中にインスタンスが生成される、スコープを表すクラスです。
 * @author Kodai Matsumoto
 */

public class Scope {
    private static int maxScopeID = 0;

    private int scopeID;
    private Scope parent;
    private List<Scope> children;
    private ArrayList<X_Symbol> variables;

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

    public void addChild(Scope scope) {
        children.add(scope);
    }

    public int getScopeID() {
        return scopeID;
    }

    public void defVar(X_Symbol sym) {
        variables.add(sym);
    }

    public void referVar(int location, X_Symbol sym) throws Exception {
        if (variables.contains(sym)) return;
        Scope s = this;
        while ((s = s.parent()) != null)
            if (s.variables.contains(sym)) return;
        throw new Exception(location + ": シンボル `" + sym + "` を解決できません");
    }
}
