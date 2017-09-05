package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.TokenType;

/**
 * 変数宣言
 * @author Kodai Matsumoto
 */

public class DeclareNode extends ExprNode {
    public DeclareNode(int n, X_Symbol symbol, Node obj) {
        super(n, TokenType.DECLARE, symbol, obj);
    }

    public Node run() throws Exception {
        X_Symbol sym = (X_Symbol)lhs;
        Node code;
        if (rhs instanceof X_Symbol) {
            code = ((X_Symbol) rhs).getAddress();
            Main.defAddress(sym, (X_Address)code);
        } else {
            code = rhs.run();
            if (code instanceof X_Address) {
                Main.defAddress(sym, (X_Address) code);
            } else {
                Main.defValue(sym, code);
            }
        }
        return code;
    }
}
