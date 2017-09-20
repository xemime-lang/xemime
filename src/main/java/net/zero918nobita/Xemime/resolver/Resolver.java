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

    public void declareVar(Symbol symbol) {
        scope.peek().defVar(symbol);
    }

    public void declareVar(Type type, Symbol symbol) {
        scope.peek().defVar(type, symbol);
    }

    public void referVar(int location, Symbol sym) throws Exception {
        scope.peek().referVar(location, sym);
    }

    public Type getTypeOfVariable(Symbol sym) throws SemanticError {
        return scope.peek().getTypeOfVariable(sym);
    }

    public void addScope() {
        scope.add(new Scope(scope.peek()));
    }

    public void removeScope() {
        scope.pop();
    }
}
