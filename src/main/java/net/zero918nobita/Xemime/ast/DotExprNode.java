package net.zero918nobita.Xemime.ast;

/**
 * フィールド参照を表すノードです。
 * @author Kodai Matsumoto
 */

public class DotExprNode extends Node {
    private Node obj;
    private Symbol symbol;

    public DotExprNode(int location, Node object, Symbol sym) {
        super(location);
        obj = object;
        symbol = sym;
    }

    @Override
    public Node run() throws Exception {
        Node o;
        o = obj.run();
        o = o.message(getLocation(), symbol);
        return o;
    }
}
