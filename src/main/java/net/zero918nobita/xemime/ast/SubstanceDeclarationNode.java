package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.entity.Address;
import net.zero918nobita.xemime.interpreter.Main;
import net.zero918nobita.xemime.lexer.TokenType;

/**
 * 実体宣言式を表すノードです。
 * @author Kodai Matsumoto
 */

public class SubstanceDeclarationNode extends ExprNode {
    public SubstanceDeclarationNode(int location, Symbol symbol, Node node) {
        super(location, TokenType.SUBST, symbol, node);
    }

    @Override
    public Node run() throws Exception {
        Symbol sym = (Symbol)lhs;
        Node result;
        if (rhs instanceof Symbol) {
            result = ((Symbol)rhs).getAddress();
            Main.defAddress(sym, (Address)result);
        } else {
            result = rhs.run();
            if (result instanceof Address) {
                Main.defAddress(sym, (Address)result);
            } else {
                Main.defValue(sym, result);
            }
        }
        return result;
    }
}
