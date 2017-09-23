package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * unit (無効な値) を表すノードです。
 * @author Kodai Matsumoto
 */

public class Unit extends Node {
    private Node body;

    public Unit(int location, Node body) {
        super(location);
        this.body = body;
    }

    @Override
    public Node run() throws Exception {
        if (body != null) body.run();
        return this;
    }

    @Override
    public String toString() {
        return "unit";
    }
}
