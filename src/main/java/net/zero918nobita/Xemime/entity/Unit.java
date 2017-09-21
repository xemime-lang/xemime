package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * unit (無効な値) を表すノードです。
 * @author Kodai Matsumoto
 */

public class Unit extends Node {
    public Unit(int location) {
        super(location);
    }

    @Override
    public String toString() {
        return "unit";
    }
}
