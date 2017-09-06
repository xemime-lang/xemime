package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

import java.util.ArrayList;

/**
 * 関数オブジェクト
 * 仮引数のリストと、呼び出されたときの評価される式を管理する
 * @author Kodai Matsumoto
 */

public abstract class Function extends Node {

    public Function(int location) {
        super(location);
    }

    /** 引数の個数 */
    int numberOfArgs = 0;

    public Node call(int location, ArrayList<Node> params, Address self) throws Exception {
        if (params == null) {
            if (numberOfArgs != 0) throw new Exception(location + ": 引数の個数が違います");
        } else {
            if (params.size() - 1 != numberOfArgs) throw new Exception(location + ": 引数の個数が違います");
        }
        return exec(params, self);
    }

    protected abstract Node exec(ArrayList<Node> params, Address self) throws Exception;
}
