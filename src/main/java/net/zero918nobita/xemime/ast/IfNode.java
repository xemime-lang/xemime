package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Bool;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
    @NotNull
    public NodeType recognize() {
        return NodeType.IF;
    }

    public ArrayList<Node> getThen() {
        return then;
    }

    public ArrayList<Node> getEls() {
        return els;
    }

    public void setThen(ArrayList<Node> then) {
        this.then = then;
    }

    public void setElse(ArrayList<Node> els) {
        this.els = els;
    }

    @Override
    public Node run() throws Exception {
        boolean cond = !condition.run().equals(Bool.getNil());
        Node result = null;
        if (cond && then != null) for (Node node : then) result = node.run();
        if (!cond && els != null) for (Node node : els) result = node.run();
        return result;
    }
}
