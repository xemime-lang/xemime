package net.zero918nobita.Xemime;

/**
 * 代入演算子
 * @author Kodai Matsumoto
 */

class X_Assign extends X_BinExpr {
    X_Assign(X_Symbol symbol, X_Object obj) {
        super(TokenType.ASSIGN, symbol, obj);
    }

    X_Object run(Environment env) throws Exception {
        X_Symbol sym = (X_Symbol)obj1;
        X_Object o = obj2.run(env);
        env.getInterpreter().set(sym, o);
        return o;
    }
}
