package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Function;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.type.Type;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 関数定義式を表すノードです。
 * @author Kodai Matsumoto
 */

public class FunctionNode extends Node {
    private int line;
    private Symbol name;
    private Type type;
    private TreeMap<Symbol, Type> params;
    private ArrayList<Node> body;

    public FunctionNode(int location, Symbol name, Type type, TreeMap<Symbol, Type> params, ArrayList<Node> body) {
        super(location);
        this.line = location;
        this.name = name;
        this.type = type;
        this.params = params;
        this.body = body;
    }

    @Override
    public Node run() throws Exception {
        Main.defValue(name, new Function(line, type, params, body));
        return new Unit(0, null);
    }
}
