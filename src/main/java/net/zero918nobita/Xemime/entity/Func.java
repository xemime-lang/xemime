package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 関数オブジェクト
 * 仮引数のリストと、呼び出されたときの評価される式を管理する
 * @author Kodai Matsumoto
 */

public abstract class Func extends Node {

    public Func(int location) {
        super(location);
    }

    /** 引数の個数 */
    int numberOfArgs = 0;

    public Node call(int location, TreeMap<Symbol, Node> params, Address self) throws Exception {
        if (params == null) {
            if (numberOfArgs != 0) throw new Exception(location + ": 引数の個数が違います");
        } else {
            if (params.size() - 1 != numberOfArgs) throw new Exception(location + ": 引数の個数が違います");
        }
        return exec(params, self);
    }

    protected abstract Node exec(TreeMap<Symbol, Node> params, Address self) throws Exception;
}
