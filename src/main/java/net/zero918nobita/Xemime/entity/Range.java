package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * 範囲リテラルを表すノードです。
 * @author Kodai Matsumoto
 */

public class Range extends Node {
    private int left;
    private boolean hasMaxElement;
    private int right;

    public Range(int location, Int left, Int right, boolean isMaxElement) throws Exception {
        super(location);
        this.hasMaxElement = isMaxElement;
        this.left = left.getValue();
        this.right = right.getValue();
    }

    public int getRight() {
        return hasMaxElement ? right : right - 1;
    }

    public int getLeft() {
        return left;
    }

    @Override
    public String toString() {
        return hasMaxElement ? left + ".." + right : left + "..." + right;
    }
}
