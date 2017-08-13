package net.zero918nobita.Xemime;

class X_DotExpr extends X_Code {
    private X_Code obj;
    private X_Symbol symbol;

    X_DotExpr(int n, X_Code o, X_Symbol sym) {
        super(n);
        obj = o;
        symbol = sym;
    }

    @Override
    X_Code run() throws Exception {
        X_Code o;
        o = obj.run();
        o = o.message(getLocation(), symbol);
        return o;
    }
}
