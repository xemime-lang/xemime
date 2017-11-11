package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * メソッドの呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class DotCallNode extends Node {
    private Node obj;
    private Symbol symbol;
    private ArrayList<Node> list;
    private LinkedHashMap<Symbol, Node> map;

    public DotCallNode(int location, Node object, Symbol sym, ArrayList<Node> list) {
        super(location);
        obj = object;
        symbol = sym;
        this.list = list;
    }

    public DotCallNode(int location, Node object, Symbol sym, LinkedHashMap<Symbol, Node> map) {
        super(location);
        obj = object;
        symbol = sym;
        this.map = map;
    }

    @Override
    public Node run() throws Exception {
        Node o = obj.run();
        if (map != null) {
            LinkedHashMap<Symbol, Node> map2 = new LinkedHashMap<>();
            for (Map.Entry<Symbol, Node> entry : map.entrySet()) map2.put(entry.getKey(), entry.getValue().run());
            map = map2;
            o = o.message(getLocation(), symbol, map);
            return o;
        } else if (list != null) {
            ArrayList<Node> list2 = new ArrayList<>();
            for (Node arg : list) list2.add(arg.run());
            list = list2;
            o = o.message(getLocation(), symbol, list);
            return o;
        } else {
            o = o.message(getLocation(), symbol, new ArrayList<>());
            return o;
        }
    }
}
