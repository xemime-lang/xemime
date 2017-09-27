package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.entity.Range;

/**
 * 範囲式を表すノードです。
 * @author Kodai Matsumoto
 */

public class RangeExprNode extends Node {
    private Node left;
    private Node right;
    private boolean hasMaxElement;

    public RangeExprNode(int location, Node left, Node right, boolean hasMaxElement) {
        super(location);
        this.left = left;
        this.right = right;
        this.hasMaxElement = hasMaxElement;
    }

    @Override
    public Node run() throws Exception {
        Node l = left.run();
        Node r = right.run();
        if (!(l instanceof Int)) throw new FatalException(left.getLocation(), 42);
        if (!(r instanceof Int)) throw new FatalException(right.getLocation(), 43);
        return new Range(getLocation(), (Int) l, (Int) r, hasMaxElement);
    }
}
