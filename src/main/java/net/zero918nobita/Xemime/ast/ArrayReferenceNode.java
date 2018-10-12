package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Array;
import net.zero918nobita.Xemime.entity.Int;

public class ArrayReferenceNode extends Node implements Recognizable {
    private Node array;
    private Node index;

    public ArrayReferenceNode(int location, Node array, Node index) {
        super(location);
        this.array = array;
        this.index = index;
    }

    @Override
    public NodeType recognize() {
        return NodeType.ARRAY_REFERENCE;
    }

    @Override
    public String toString() {
        return (array instanceof Symbol) ? array + "[" + index + "]" : "(" + array + ")[" + index + "]";
    }

    public Node getArray() {
        return array;
    }

    @Override
    public Node run() throws Exception {
        Node eArray = array.run();
        Node eIndex = index.run();
        if (!(eArray instanceof Array)) throw new FatalException(getLocation(), 135);
        if (!(eIndex instanceof Int)) throw new FatalException(getLocation(), 136);
        if (((Int) eIndex).getValue() < 0) throw new RuntimeException(getLocation() + ": 配列参照`" + this + "` の添え字 `" + index + "` が 0 未満になっているため、要素を取り出すことができません。 [137]");
        if (((Int) eIndex).getValue() >= ((Array) eArray).getElements().size())
            throw new RuntimeException(getLocation() + ": 配列参照 `" + this + "` の添え字 `" + index + "` が配列 `" + array + "` の要素数以上になっているため、要素を取り出すことができません。 [138]");
        return ((Array) eArray).getElement(((Int) eIndex).getValue());
    }
}
