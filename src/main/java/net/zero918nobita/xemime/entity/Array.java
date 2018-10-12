package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.ast.Node;
import net.zero918nobita.xemime.type.Type;

import java.util.ArrayList;

public class Array extends Node implements Recognizable {
    private Type type;
    private ArrayList<Node> elements;

    public Array(int location, Type type, ArrayList<Node> elements) {
        super(location);
        this.type = type;
        this.elements = elements;
    }

    @Override
    public NodeType recognize() {
        return NodeType.ARRAY;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public Node run() throws Exception {
        return this;
    }

    public Node getElement(int index) {
        if (index >= elements.size()) throw new ArrayIndexOutOfBoundsException();
        return elements.get(index);
    }

    public ArrayList<Node> getElements() {
        return elements;
    }

    public Type getType() {
        return type;
    }
}
