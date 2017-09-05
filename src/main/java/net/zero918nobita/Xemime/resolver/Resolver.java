package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.Symbol;

import java.util.Stack;

/**
 * 変数の参照の解決を行います。
 * @author Kodai Matsumoto
 */

public class Resolver {
    private Stack<Scope> scope = new Stack<>();

    public Resolver() {
        scope.add(new Scope(null));
    }

    public void declareVar(Symbol sym) {
        scope.peek().defVar(sym);
    }

    public void referVar(int location, Symbol sym) throws Exception {
        scope.peek().referVar(location, sym);
    }

    public void addScope() {
        scope.add(new Scope(scope.peek()));
    }

    public void removeScope() {
        scope.pop();
    }
}
