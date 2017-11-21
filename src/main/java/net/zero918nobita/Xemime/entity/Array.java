package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.type.Type;

import java.util.ArrayList;

/**
 * 配列
 * @author Kodai Matsumoto
 */

public class Array extends Node {
    private Type type;
    private ArrayList<Node> elements;

    public Array(int location, Type type, ArrayList<Node> elements) {
        super(location);
        this.type = type;
        this.elements = elements;
    }

    @Override
    public Node run() throws Exception {
        return this;
    }

    public void addElement(Node node) {
        elements.add(node);
    }

    public Node getElement(int index) {
        if (index >= elements.size()) throw new ArrayIndexOutOfBoundsException();
        return elements.get(index);
    }
}
