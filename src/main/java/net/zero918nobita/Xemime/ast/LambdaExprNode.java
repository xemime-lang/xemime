package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;

/**
 * ラムダ式
 * @author Kodai Matsumoto
 */

public class LambdaExprNode extends Node {
    private int line;
    private ArrayList<X_Symbol> params;
    private Node body;

    public LambdaExprNode(int n, ArrayList<X_Symbol> l, Node obj) throws Exception {
        super(n);
        line = n;
        params = l;
        body = obj;
    }

    @Override
    public Node run() throws Exception {
        X_Handler table = new X_Handler(0);
        for (X_Symbol sym : params) table.setMember(sym, Main.register(X_Bool.Nil));
        Main.loadLocalFrame(table);
        if (body instanceof LambdaExprNode) body = body.run();
        Main.unloadLocalFrame();
        return new X_Closure(line, params, body, Main.frame);
    }
}
