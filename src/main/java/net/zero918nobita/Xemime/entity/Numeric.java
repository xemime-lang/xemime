package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * 数値オブジェクト
 * @author Kodai Matsumoto
 */

abstract class Numeric extends Node {
    protected Number value;

    Numeric(int location) {
        super(location);
    }
}
