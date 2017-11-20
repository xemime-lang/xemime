package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Bool;

import java.util.ArrayList;

/**
 * if 文を表すノードです。
 * @author Kodai Matsumoto
 */

public class IfNode extends Node implements Recognizable {
    private Node condition;
    private ArrayList<Node> then;
    private ArrayList<Node> els;

    public IfNode(int location, Node condition, ArrayList<Node> then, ArrayList<Node> els) {
        super(location);
        this.condition = condition;
        this.then = then;
        this.els = els;
    }

    @Override
    public NodeType recognize() {
        return NodeType.IF;
    }

    public ArrayList<Node> getThen() {
        return then;
    }

    public ArrayList<Node> getEls() {
        return els;
    }

    @Override
    public Node run() throws Exception {
        boolean cond = !condition.run().equals(Bool.Nil);
        Node result = null;
        if (cond && then != null) for (Node node : then) result = node.run();
        if (!cond && els != null) for (Node node : els) result = node.run();
        return result;
    }
}
