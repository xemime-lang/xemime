package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Bool;
import net.zero918nobita.xemime.entity.Unit;

import java.util.ArrayList;

/**
 * while 文を表すノードです。
 * @author Kodai Matsumoto
 */

public class WhileNode extends Node implements Recognizable {
    private Node condition;
    private ArrayList<Node> body;

    public WhileNode(int location, Node condition, ArrayList<Node> body) {
        super(location);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public NodeType recognize() {
        return NodeType.WHILE;
    }

    @Override
    public Node run() throws Exception {
        while (!condition.run().equals(Bool.Nil))
            for (Node node : body) node.run();
        return new Unit(getLocation(), null);
    }
}
