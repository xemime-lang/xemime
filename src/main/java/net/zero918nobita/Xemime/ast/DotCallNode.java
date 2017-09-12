package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * メソッドの呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class DotCallNode extends Node {
    private Node obj;
    private Symbol symbol;
    private ArrayList<Node> list;

    public DotCallNode(int n, Node o, Symbol sym, ArrayList<Node> l) {
        super(n);
        obj = o;
        symbol = sym;
        list = l;
    }

    @Override
    public Node run() throws Exception {
        Node o;
        o = obj.run();
        if (list != null) {
            ArrayList<Node> list2 = new ArrayList<>();
            for (Node arg : list) list2.add(arg.run());
            list = list2;
        }
        o = o.message(getLocation(), symbol, list);
        return o;
    }
}
