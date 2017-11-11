package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.ReturnNode;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.type.Type;
import net.zero918nobita.Xemime.type.UnitType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Function extends Func {
    private Type type;
    private LinkedHashMap<Symbol, Type> params;
    private ArrayList<Node> body;

    public Function(int location, Type type, LinkedHashMap<Symbol, Type> params, ArrayList<Node> body) {
        super(location);
        this.type = type;
        this.params = params;
        this.body = body;
        this.numberOfArgs = params.size();
    }

    @Override
    public Node run() throws Exception {
        return this;
    }

    protected Node exec(LinkedHashMap<Symbol, Node> args, Address self) throws Exception {
        Main.loadLocalFrame(new Handler(0));
        for (Map.Entry<Symbol, Node> entry : args.entrySet()) Main.defValue(entry.getKey(), entry.getValue());
        Node result;
        for (Node node : body) {
            result = node.run();
            if (result instanceof ReturnNode) return ((ReturnNode) result).getValue();
        }
        if (!(type instanceof UnitType)) throw new FatalException(getLocation(), 65);
        Main.unloadLocalFrame();
        return new Unit(0, null);
    }

    protected Node exec(ArrayList<Node> args, Address self) throws Exception {
        return exec(new LinkedHashMap<Symbol, Node>(){{
            int i = 1;
            for (Map.Entry<Symbol, Type> entry : params.entrySet()) {
                put(entry.getKey(), args.get(i));
                i ++;
            }
        }}, self);
    }
}
