package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.Pair;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.parser.FatalError;
import net.zero918nobita.Xemime.type.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * 変数の参照の解決を行います。
 * @author Kodai Matsumoto
 */

public class Resolver {
    private Stack<Scope> scope = new Stack<>();
    private HashSet<Symbol> substs = new HashSet<>();
    private StaticTypeChecker stc = new StaticTypeChecker();

    public Resolver() {
        scope.add(new Scope(null));
    }

    public void declareVar(Symbol symbol, Node node) throws FatalError, SemanticError, TypeError {
        scope.peek().defVar(stc.check(this, node), symbol);
    }

    public void declareVar(Type type, Symbol symbol) {
        scope.peek().defVar(type, symbol);
    }

    public void assignVar(int location, Symbol symbol, Node node) throws FatalError, SemanticError, TypeError {
        if (!scope.peek().hasVariable(symbol)) throw new SemanticError(location, 51, symbol);
        Type type_of_variable = getTypeOfVariable(symbol);
        Type type_of_value = stc.check(this, node);
        if (!type_of_variable.equals(type_of_value)) throw new TypeError(location, 87, "代入式が不正です。変数の型と代入される値の型が一致しません。");
    }

    public void referVar(int location, Symbol sym) throws SemanticError {
        scope.peek().referVar(location, sym);
    }

    public Type getTypeOfNode(Node node) throws FatalError, SemanticError, TypeError {
        return stc.check(this, node);
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

    public void defineAttr(Symbol symbol) throws Exception {
        if (substs.contains(symbol)) throw new Exception(symbol + "型はすでに定義されています。");
        substs.add(symbol);
    }

    public boolean hasAttr(Symbol symbol) {
        return substs.contains(symbol);
    }
}
