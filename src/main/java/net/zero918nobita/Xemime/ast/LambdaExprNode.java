package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.entity.Closure;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;

/**
 * ラムダ式を表すノードです。
 * @author Kodai Matsumoto
 */

public class LambdaExprNode extends Node {
    private int line;
    private ArrayList<Symbol> params;
    private Node body;

    public LambdaExprNode(int n, ArrayList<Symbol> l, Node obj) throws Exception {
        super(n);
        line = n;
        params = l;
        body = obj;
    }

    @Override
    public Node run() throws Exception {
        Handler table = new Handler(0);
        for (Symbol sym : params) table.setMember(sym, Main.register(Bool.Nil));
        Main.loadLocalFrame(table);
        if (body instanceof LambdaExprNode) body = body.run();
        Main.unloadLocalFrame();
        return new Closure(line, params, body, Main.frame);
    }
}
