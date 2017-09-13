package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Function;
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
        if (node instanceof Symbol || node instanceof Native || node instanceof FuncallNode) {
            func = node;
        } else {
            throw new Exception(getLocation() + ": 深刻なエラー: 関数呼び出しに失敗しました");
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
        } else if (func instanceof FuncallNode) {
            Node c = func.run();
            if (c == null) throw new Exception(getLocation() + ": 存在しない関数です");
            if (!(c instanceof Function)) throw new Exception(getLocation() + ": 関数ではありません");
            Function f = (Function)c;
            ArrayList<Node> params = new ArrayList<>();
            for (Node o: list) params.add(o.run());
            params.add(0, f);
            return f.call(getLocation(), params, null);
        } else {
            Symbol symbol = (Symbol)func;
            Node c = Main.getValueOfSymbol(symbol);
            if (c == null) throw new Exception(getLocation() + ": 関数 `" + symbol.getName() + "` は存在しません");
            if (!(c instanceof Function)) throw new Exception(getLocation() + ": 変数 `" + symbol.getName() + "` には関数オブジェクトが代入されていません");
            Function func = (Function) c;
            ArrayList<Node> params = new ArrayList<>();
            for (Node o : list) params.add(o.run());
            params.add(0, func);
            return func.call(getLocation(), params, null);
        }
    }
}
