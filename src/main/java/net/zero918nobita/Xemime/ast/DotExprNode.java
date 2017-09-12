package net.zero918nobita.Xemime.ast;

/**
 * フィールド参照を表すノードです。
 * @author Kodai Matsumoto
 */

public class DotExprNode extends Node {
    private Node obj;
    private Symbol symbol;

    public DotExprNode(int n, Node o, Symbol sym) {
        super(n);
        obj = o;
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
