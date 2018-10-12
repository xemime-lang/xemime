package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.ast.Node;

public class Range extends Node {
    private int left;
    private boolean hasMaxElement;
    private int right;

    public Range(int location, Int left, Int right, boolean isMaxElement) {
        super(location);
        this.hasMaxElement = isMaxElement;
        this.left = left.getValue().intValue();
        this.right = right.getValue().intValue();
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
