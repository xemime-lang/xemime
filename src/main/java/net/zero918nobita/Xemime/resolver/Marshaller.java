package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.FunctionNode;
import net.zero918nobita.Xemime.ast.IfNode;
import net.zero918nobita.Xemime.ast.Node;

import java.util.ArrayList;

public class Marshaller {
    public static ArrayList<Node> marshal(ArrayList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i ++) {
            if (nodes.get(i) instanceof FunctionNode) {
                FunctionNode functionNode = (FunctionNode) nodes.get(i);
                functionNode.setBody(marshal(functionNode.getBody()));
                nodes.remove(i);
                nodes.add(0, functionNode);
                continue;
            }
            if (nodes.get(i) instanceof IfNode) {
                IfNode ifNode = (IfNode) nodes.get(i);
                ifNode.setThen(marshal(ifNode.getThen()));
                if (ifNode.getEls() != null) marshal(ifNode.getEls());
            }
        }
        return nodes;
    }
}
