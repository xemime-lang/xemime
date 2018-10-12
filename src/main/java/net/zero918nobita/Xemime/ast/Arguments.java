package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/** 名前付き/無し引数リスト */
public class Arguments {
    private LinkedHashMap<Symbol, Node> params;

    public Arguments(LinkedHashMap<Symbol, Node> params) {
        this.params = params;
    }

    public LinkedHashMap<Symbol, Node> getParams() {
        return params;
    }

    public Node getParam(int index) {
        ArrayList<Node> args = new ArrayList<>();
        params.forEach((symbol, node) -> args.add(node));
        return args.get(index);
    }

    public Node getParam(Symbol name) {
        return params.get(name);
    }
}
