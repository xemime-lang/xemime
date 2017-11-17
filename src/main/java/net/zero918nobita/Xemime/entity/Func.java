package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 関数オブジェクト
 * 仮引数のリストと、呼び出されたときの評価される式を管理する
 * @author Kodai Matsumoto
 */

public abstract class Func extends Node implements Recognizable {

    Func(int location) {
        super(location);
    }

    @Override
    public NodeType recognize() {
        return NodeType.FUNC;
    }

    /** 引数の個数 */
    int numberOfArgs = 0;

    public Node call(int location, LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
        if (params == null) {
            if (numberOfArgs != 0) throw new Exception(location + ": 引数の個数が違います");
        } else {
            if (params.size() - 1 != numberOfArgs) throw new Exception(location + ": 引数の個数が違います");
        }
        return exec(params, self);
    }

    public Node call(int location, ArrayList<Node> params, Address self) throws Exception {
        if (params == null) {
            if (numberOfArgs != 0) throw new Exception(location + ": 引数の個数が違います");
        } else {
            if (params.size() - 1 != numberOfArgs) throw new Exception(location + ": 引数の個数が違います");
        }
        return exec(params, self);
    }

    protected abstract Node exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception;

    protected abstract Node exec(ArrayList<Node> params, Address self) throws Exception;
}
