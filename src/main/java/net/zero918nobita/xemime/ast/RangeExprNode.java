package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Int;
import net.zero918nobita.xemime.entity.Range;
import org.jetbrains.annotations.NotNull;

/** 範囲式 */
public class RangeExprNode extends Node implements Recognizable {
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
    @NotNull
    public NodeType recognize() {
        return NodeType.RANGE_EXPR;
    }

    @Override
    @NotNull
    public Node run() throws Exception {
        Node l = left.run();
        Node r = right.run();

        // Fatal Exception - 範囲式の左辺が整数値ではありません。
        if (!(l instanceof Int)) throw new FatalException(left.getLocation(), 42);

        // Fatal Exception - 範囲式の右辺が整数値ではありません。
        if (!(r instanceof Int)) throw new FatalException(right.getLocation(), 43);

        return new Range(getLocation(), ((Int) l).getValue(), ((Int) r).getValue(), hasMaxElement);
    }
}
