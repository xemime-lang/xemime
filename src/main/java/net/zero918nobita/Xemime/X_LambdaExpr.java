package net.zero918nobita.Xemime;

import java.util.ArrayList;

class X_LambdaExpr extends X_Code {
    private int line;
    private ArrayList<X_Symbol> params;
    private X_Code body;

    X_LambdaExpr(int n, ArrayList<X_Symbol> l, X_Code obj) throws Exception {
        super(n);
        line = n;
        params = l;
        body = obj;
    }

    @Override
    X_Code run() throws Exception {
        X_Handler table = new X_Handler(0);
        for (X_Symbol sym : params) table.setMember(sym, Main.register(X_Bool.Nil));
        Main.loadLocalFrame(table);
        if (body instanceof X_LambdaExpr) body = body.run();
        Main.unloadLocalFrame();
        return new X_Closure(line, params, body, Main.frame);
    }
}
