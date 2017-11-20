package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Function;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.type.Type;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 関数定義式を表すノードです。
 * @author Kodai Matsumoto
 */

public class FunctionNode extends Node implements Recognizable {
    private int line;
    private Symbol name;
    private Type type;
    private LinkedHashMap<Symbol, Type> params;
    private ArrayList<Node> body;

    public FunctionNode(int location, Symbol name, Type type, LinkedHashMap<Symbol, Type> params, ArrayList<Node> body) {
        super(location);
        this.line = location;
        this.name = name;
        this.type = type;
        this.params = params;
        this.body = body;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FunctionNode)) return false;
        FunctionNode functionNode = (FunctionNode) object;
        return (line == functionNode.line && name.equals(functionNode.name) &&
            params.equals(functionNode.params));
    }

    @Override
    public NodeType recognize() {
        return NodeType.FN;
    }

    @Override
    public Node run() throws Exception {
        Main.defValue(name, new Function(line, type, params, body));
        return new Unit(0, null);
    }
}
