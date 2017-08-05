package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * 関数オブジェクト
 * 仮引数のリストと、呼び出されたときの評価される式を管理する
 * @author Kodai Matsumoto
 */

abstract class X_Function extends X_Object {

    /** 引数の個数 */
    protected int numberOfArgs = 0;

    X_Object call(ArrayList<X_Object> params) throws Exception {
        if (params == null) {
            if (numberOfArgs != 0) throw new Exception("引数の個数が違います");
        } else {
            if (params.size() != numberOfArgs) throw new Exception("引数の個数が違います");
        }
        return exec(params);
    }

    protected abstract X_Object exec(ArrayList<X_Object> params) throws Exception;
}
