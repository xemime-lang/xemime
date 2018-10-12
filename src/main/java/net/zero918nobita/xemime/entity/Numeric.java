package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.ast.Node;

/**
 * 数値を表すノードです。
 * @author Kodai Matsumoto
 */

public abstract class Numeric extends Node {
    protected Number value;

    Numeric(int location) {
        super(location);
    }
}
