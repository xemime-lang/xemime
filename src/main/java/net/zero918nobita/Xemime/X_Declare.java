package net.zero918nobita.Xemime;

/**
 * 変数宣言
 * @author Kodai Matsumoto
 */

class X_Declare extends X_BinExpr {
    X_Declare(int n, X_Symbol symbol, X_Code obj) {
        super(n, TokenType.DECLARE, symbol, obj);
    }

    X_Code run() throws Exception {
        X_Symbol sym = (X_Symbol)obj1;
        X_Code o = obj2.run();
        if (o instanceof X_Address) {
            Main.defAddress(sym, (X_Address)o);
        } else {
            Main.defValue(sym, o);
        }
        return o;
    }
}
