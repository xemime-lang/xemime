package net.zero918nobita.Xemime.ast;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * メソッドの呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class DotCallNode extends Node {
    private Node obj;
    private Symbol symbol;
    private LinkedHashMap<Symbol, Node> list;

    public DotCallNode(int location, Node object, Symbol sym, LinkedHashMap<Symbol, Node> list) {
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
            LinkedHashMap<Symbol, Node> list2 = new LinkedHashMap<>();
            for (Map.Entry<Symbol, Node> entry : list.entrySet()) list2.put(entry.getKey(), entry.getValue().run());
            list = list2;
        }
        o = o.message(getLocation(), symbol, list);
        return o;
    }
}
