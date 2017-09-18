package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

import java.util.ArrayList;

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

    public Node exec(ArrayList<Node> params, Address dynamicSelf) throws Exception {
        return func.exec(params, dynamicSelf);
    }
}
