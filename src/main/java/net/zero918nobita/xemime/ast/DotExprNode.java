package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;

/**
 * フィールド参照を表すノードです。
 * @author Kodai Matsumoto
 */

public class DotExprNode extends Node implements Recognizable {
    private Node obj;
    private Symbol symbol;

    public DotExprNode(int location, Node object, Symbol sym) {
        super(location);
        obj = object;
        symbol = sym;
    }

    @Override
    public NodeType recognize() {
        return NodeType.DOT_EXPR;
    }

    @Override
    public Node run() throws Exception {
        Node o;
        o = obj.run();
        o = o.message(getLocation(), symbol);
        return o;
    }
}
