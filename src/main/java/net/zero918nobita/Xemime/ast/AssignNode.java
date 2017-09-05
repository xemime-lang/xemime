package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.TokenType;

/**
 * 代入演算子を表すノードです。
 * @author Kodai Matsumoto
 */

public class AssignNode extends ExprNode {
    public AssignNode(int n, X_Symbol symbol, Node obj) {
        super(n, TokenType.ASSIGN, symbol, obj);
    }

    public Node run() throws Exception {
        X_Symbol sym = (X_Symbol)lhs;
        if (!Main.hasSymbol(sym)) throw new Exception(getLocation() + ": `" + sym.getName() + "` 未宣言のシンボルです");
        Node code;
        if (rhs instanceof X_Symbol) {
            code = ((X_Symbol) rhs).getAddress();
            Main.defAddress(sym, (X_Address)code);
        } else {
            code = rhs.run();
            if (code instanceof X_Address) {
                Main.setAddress(sym, (X_Address) code);
            } else {
                Main.setValue(sym, code);
            }
        }
        return code;
    }
}
