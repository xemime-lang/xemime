package net.zero918nobita.Xemime;

class X_DotExpr extends X_Object {
    private X_Object obj;
    private X_Symbol symbol;

    X_DotExpr(X_Object o, X_Symbol sym) {
        obj = o;
        symbol = sym;
    }

    @Override
    X_Object run() throws Exception {
        X_Object o;
        o = obj.run();
        o = o.message(symbol);
        return o;
    }
}
