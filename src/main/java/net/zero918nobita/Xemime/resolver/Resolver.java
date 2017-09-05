package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.X_Code;
import net.zero918nobita.Xemime.ast.X_Symbol;
import net.zero918nobita.Xemime.entity.Scope;

import java.util.ArrayList;
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

    public void resolve(ArrayList<X_Code> c) {
        for (X_Code elm : c) resolve(elm);
    }

    public void resolve(X_Code c) {
    }

    public void declareVar(X_Symbol sym) {
        scope.peek().defVar(sym);
    }

    public void referVar(int location, X_Symbol sym) throws Exception {
        scope.peek().referVar(location, sym);
    }

    public void addScope() {
        scope.add(new Scope(scope.peek()));
    }

    public void removeScope() {
        scope.pop();
    }
}
