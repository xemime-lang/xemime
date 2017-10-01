package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.TreeMap;

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

    public Node exec(TreeMap<Symbol, Node> params, Address dynamicSelf) throws Exception {
        return func.exec(params, dynamicSelf);
    }
}
