package net.zero918nobita.Xemime;

/**
 * 代入演算子
 * @author Kodai Matsumoto
 */

class X_Assign extends X_BinExpr {
    X_Assign(int n, X_Symbol symbol, X_Code obj) {
        super(n, TokenType.ASSIGN, symbol, obj);
    }

    X_Code run() throws Exception {
        X_Symbol sym = (X_Symbol)obj1;
        if (!Main.hasSymbol(sym)) throw new Exception(getLocation() + ": `" + sym.getName() + "` 未宣言のシンボルです");
        X_Code code;
        if (obj2 instanceof X_Symbol) {
            code = ((X_Symbol) obj2).getAddress();
            Main.defAddress(sym, (X_Address)code);
        } else {
            code = obj2.run();
            if (code instanceof X_Address) {
                Main.defAddress(sym, (X_Address) code);
            } else {
                Main.defValue(sym, code);
            }
        }
        return code;
    }
}
