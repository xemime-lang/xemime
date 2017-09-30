package net.zero918nobita.Xemime.ast;

/**
 * return 式を表すノードです。
 * @author Kodai Matsumoto
 */

public class ReturnNode extends Node {
    private Node value;

    public ReturnNode(int location, Node value) {
        super(location);
        this.value = value;
    }

    @Override
    public Node run() throws Exception {
        return this;
    }
}
