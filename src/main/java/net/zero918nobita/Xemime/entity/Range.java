package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * 範囲式を表すノードです。
 * @author Kodai Matsumoto
 */

public class Range extends Node {
    private boolean hasMaxElement;
    private Node left;
    private boolean hasMinElement;
    private Node right;

    public Range(int location, Node left, boolean isMinElement, Node right, boolean isMaxElement) {
        super(location);
        this.hasMaxElement = isMaxElement;
        this.hasMinElement = isMinElement;

    }

    Node getMaxElement() {
        return (hasMaxElement) ? right : null;
    }

    Node getMinElement() {
        return (hasMinElement) ? left : null;
    }
}
