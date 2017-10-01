package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Func;
import net.zero918nobita.Xemime.entity.Native;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.Map;
import java.util.TreeMap;

/**
 * 関数呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class FuncallNode extends Node {
    private Node func;
    private TreeMap<Symbol, Node> list;

    public FuncallNode(int location, Node node, TreeMap<Symbol, Node> list) throws Exception {
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
            TreeMap<Symbol, Node> params = new TreeMap<>();
            for (Map.Entry<Symbol, Node> entry : list.entrySet()) params.put(entry.getKey(), entry.getValue().run());
            params.put(Symbol.intern(0, "this"), func);
            return ((Native) func).call(getLocation(), params, null);
        } else {
            Symbol symbol = (Symbol)func;
            Node c = Main.getValueOfSymbol(symbol);

            // Fatal Exception - 指定された関数は存在しません。
            if (c == null) throw new FatalException(getLocation(), 12);

            // Fatal Exception - 呼び出し対象が関数ではありません。
            if (!(c instanceof Func)) throw new FatalException(getLocation(), 13);

            Func func = (Func) c;
            TreeMap<Symbol, Node> params = new TreeMap<>();
            for (Map.Entry<Symbol, Node> entry : list.entrySet()) params.put(entry.getKey(), entry.getValue().run());
            params.put(Symbol.intern(0, "this"), func);
            return func.call(getLocation(), params, null);
        }
    }
}
