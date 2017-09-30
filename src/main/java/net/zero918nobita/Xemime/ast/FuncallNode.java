package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Func;
import net.zero918nobita.Xemime.entity.Native;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;

/**
 * 関数呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class FuncallNode extends Node {
    private Node func;
    private ArrayList<Node> list;

    public FuncallNode(int location, Node node, ArrayList<Node> list) throws Exception {
        super(location);
        if (node instanceof Symbol || node instanceof Native) {
            func = node;
        } else {
            // Fatal Exception - 関数呼び出しに失敗しました。
            throw new FatalException(getLocation(), 9);
        }
        this.list = list;
    }

    @Override
    public Node run() throws Exception {
        if (func instanceof Native) {
            ArrayList<Node> params = new ArrayList<>();
            for (Node o : list) params.add(o.run());
            params.add(0, func);
            return ((Native) func).call(getLocation(), params, null);
        } else {
            Symbol symbol = (Symbol)func;
            Node c = Main.getValueOfSymbol(symbol);

            // Fatal Exception - 指定された関数は存在しません。
            if (c == null) throw new FatalException(getLocation(), 12);

            // Fatal Exception - 呼び出し対象が関数ではありません。
            if (!(c instanceof Func)) throw new FatalException(getLocation(), 13);

            Func func = (Func) c;
            ArrayList<Node> params = new ArrayList<>();
            for (Node o : list) params.add(o.run());
            params.add(0, func);
            return func.call(getLocation(), params, null);
        }
    }
}
