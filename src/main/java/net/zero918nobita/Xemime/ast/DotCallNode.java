package net.zero918nobita.Xemime.ast;

import java.util.Map;
import java.util.TreeMap;

/**
 * メソッドの呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class DotCallNode extends Node {
    private Node obj;
    private Symbol symbol;
    private TreeMap<Symbol, Node> list;

    public DotCallNode(int location, Node object, Symbol sym, TreeMap<Symbol, Node> list) {
        super(location);
        obj = object;
        symbol = sym;
        this.list = list;
    }

    @Override
    public Node run() throws Exception {
        Node o;
        o = obj.run();
        if (list != null) {
            TreeMap<Symbol, Node> list2 = new TreeMap<>();
            for (Map.Entry<Symbol, Node> entry : list.entrySet()) list2.put(entry.getKey(), entry.getValue().run());
            list = list2;
        }
        o = o.message(getLocation(), symbol, list);
        return o;
    }
}
