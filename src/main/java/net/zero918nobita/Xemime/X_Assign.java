package net.zero918nobita.Xemime;

/**
 * 代入演算子
 * @author Kodai Matsumoto
 */

class X_Assign extends X_BinExpr {
    X_Assign(X_Symbol symbol, X_Code obj) {
        super(TokenType.ASSIGN, symbol, obj);
    }

    X_Code run() throws Exception {
        X_Symbol sym = (X_Symbol)obj1;
        X_Code o = obj2.run();
        if (o instanceof X_Address) {
            Main.setAddress(sym, (X_Address)o);
        } else {
            Main.setValue(sym, o);
        }
        return o;
    }
}
