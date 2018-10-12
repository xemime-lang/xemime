package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;

/**
 * return 式を表すノードです。
 * @author Kodai Matsumoto
 */

public class ReturnNode extends Node implements Recognizable {
    private Node value;

    public ReturnNode(int location, Node value) {
        super(location);
        this.value = value;
    }

    @Override
    public NodeType recognize() {
        return NodeType.RETURN;
    }

    @Override
    public Node run() throws Exception {
        return this;
    }

    public Node getValue() throws Exception {
        return value.run();
    }
}
