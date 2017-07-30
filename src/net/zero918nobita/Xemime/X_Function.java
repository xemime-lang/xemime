package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * 関数オブジェクト
 * 仮引数のリストと、呼び出されたときの評価される式を管理する
 * @author Kodai Matsumoto
 */

class X_Function extends X_Object {
    /**
     * 仮引数のリスト
     */
    private ArrayList<X_Symbol> arguments;
    /**
     * 呼び出された時に評価される式
     */
    private String formula;

    X_Function(ArrayList<X_Symbol> args, String expr) {
        arguments = args;
        formula = expr;
    }

    // X_Object call() {}
}
