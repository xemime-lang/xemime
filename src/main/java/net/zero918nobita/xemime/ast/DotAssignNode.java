package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Address;
import net.zero918nobita.xemime.entity.Handler;
import net.zero918nobita.xemime.interpreter.Main;

/**
 * オブジェクトのメンバを更新する代入演算子
 * @author Kodai Matsumoto
 */

public class DotAssignNode extends Node implements Recognizable {
    private Node code1;
    private Symbol symbol;
    private Node code2;

    public DotAssignNode(int location, Node object, Symbol sym, Node rhs) {
        super(location);
        code1 = object;
        symbol = sym;
        code2 = rhs;
    }

    @Override
    public NodeType recognize() {
        return NodeType.DOT_ASSIGN;
    }

    @Override
    public Node run() throws Exception {
        Node c1 = code1.run();
        Node c2 = code2.run();
        if (c1 instanceof Handler) {
            if (c2 instanceof Address) {
                ((Handler) c1).setMember(symbol, c2);
            } else {
                ((Handler) c1).setMember(symbol, Main.register(c2));
            }
        } else {
            throw new Exception(getLocation() + ": メンバを設定することができません");
        }
        return c2;
    }
}
