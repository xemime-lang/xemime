package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.entity.Unit;

import java.util.ArrayList;

/**
 * while 文を表すノードです。
 * @author Kodai Matsumoto
 */

public class WhileNode extends Node {
    private Node condition;
    private ArrayList<Node> body;

    public WhileNode(int location, Node condition, ArrayList<Node> body) {
        super(location);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public Node run() throws Exception {
        while (!condition.run().equals(Bool.Nil)) {
            for (Node node : body) node.run();
        }
        return new Unit(getLocation(), null);
    }
}
