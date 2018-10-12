package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Address;
import net.zero918nobita.xemime.interpreter.Main;
import net.zero918nobita.xemime.lexer.TokenType;

/**
 * 変数宣言式を表すノードです。
 * @author Kodai Matsumoto
 */

public class DeclarationNode extends ExprNode implements Recognizable {
    public DeclarationNode(int location, Symbol symbol, Node node) {
        super(location, TokenType.DECLARE, symbol, node);
    }

    @Override
    public NodeType recognize() {
        return NodeType.DECLARATION;
    }

    public Node run() throws Exception {
        Symbol sym = (Symbol)lhs;
        Node result;
        if (rhs instanceof Symbol) {
            result = ((Symbol) rhs).getAddress();
            Main.defAddress(sym, (Address)result);
        } else {
            result = rhs.run();
            if (result instanceof Address) {
                Main.defAddress(sym, (Address) result);
            } else {
                Main.defValue(sym, result);
            }
        }
        return result;
    }
}
