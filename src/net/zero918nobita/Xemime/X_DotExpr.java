package net.zero918nobita.Xemime;

class X_DotExpr extends X_Code {
    private X_Code obj;
    private X_Symbol symbol;

    X_DotExpr(X_Code o, X_Symbol sym) {
        obj = o;
        symbol = sym;
    }

    @Override
    X_Code run() throws Exception {
        X_Code o;
        o = obj.run();
        o = o.message(symbol);
        return o;
    }
}
