package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.ast.Node;
import net.zero918nobita.xemime.ast.Symbol;

import java.util.LinkedHashMap;

/**
 * 属性を表すノードです。
 * @author Kodai Matsumoto
 */

public class Attr extends Node {
    private Closure func;

    public Attr(int location, Closure func) {
        super(location);
        this.func = func;
    }

    public Node exec(LinkedHashMap<Symbol, Node> params, Address dynamicSelf) throws Exception {
        return func.exec(params, dynamicSelf);
    }
}
