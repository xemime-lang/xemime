package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Address;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.TokenType;

/**
 * 代入演算子を表すノードです。
 * @author Kodai Matsumoto
 */

public class AssignNode extends ExprNode {
    public AssignNode(int location, Symbol symbol, Node node) {
        super(location, TokenType.ASSIGN, symbol, node);
    }

    public Node run() throws Exception {
        Symbol sym = (Symbol)lhs;
        if (!Main.hasSymbol(sym)) throw new Exception(getLocation() + ": `" + sym.getName() + "` 未宣言のシンボルです");
        Node code;
        if (rhs instanceof Symbol) {
            code = ((Symbol) rhs).getAddress();
            Main.defAddress(sym, (Address)code);
        } else {
            code = rhs.run();
            if (code instanceof Address) {
                Main.setAddress(sym, (Address) code);
            } else {
                Main.setValue(sym, code);
            }
        }
        return code;
    }
}
