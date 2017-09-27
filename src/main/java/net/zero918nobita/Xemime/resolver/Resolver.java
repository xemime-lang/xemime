package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.Stack;

/**
 * 変数の参照の解決を行います。
 * @author Kodai Matsumoto
 */

public class Resolver {
    private Stack<Scope> scope = new Stack<>();
    private StaticTypeChecker stc = new StaticTypeChecker();

    public Resolver() {
        scope.add(new Scope(null));
    }

    public void declareVar(Symbol symbol, Node node) throws Exception {
        scope.peek().defVar(stc.check(this, node), symbol);
    }

    public void declareVar(Type type, Symbol symbol) throws Exception {
        if (type != stc.check(this, symbol)) throw new Exception("型が一致しません。");
        scope.peek().defVar(type, symbol);
    }

    public void referVar(int location, Symbol sym) throws Exception {
        scope.peek().referVar(location, sym);
    }

    Type getTypeOfVariable(Symbol sym) throws SemanticError {
        return scope.peek().getTypeOfVariable(sym);
    }

    public void addScope() {
        scope.add(new Scope(scope.peek()));
    }

    public void removeScope() {
        scope.pop();
    }
}
