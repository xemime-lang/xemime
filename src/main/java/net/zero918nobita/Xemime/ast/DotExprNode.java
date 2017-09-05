package net.zero918nobita.Xemime.ast;

public class DotExprNode extends Node {
    private Node obj;
    private X_Symbol symbol;

    public DotExprNode(int n, Node o, X_Symbol sym) {
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
