package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;

/**
 * 変数宣言
 * @author Kodai Matsumoto
 */

public class X_DeclareNode extends ExprNode {
    public X_DeclareNode(int n, X_Symbol symbol, X_Code obj) {
        super(n, TokenType.DECLARE, symbol, obj);
    }

    public X_Code run() throws Exception {
        X_Symbol sym = (X_Symbol)obj1;
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
