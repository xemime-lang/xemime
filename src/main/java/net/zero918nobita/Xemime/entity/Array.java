package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.parser.FatalError;
import net.zero918nobita.Xemime.resolver.SemanticError;
import net.zero918nobita.Xemime.resolver.TypeError;
import net.zero918nobita.Xemime.type.Type;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * 配列
 * @author Kodai Matsumoto
 */

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

    public void addElement(Node node) {
        elements.add(node);
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
