package net.zero918nobita.Xemime;

/**
 * 数値オブジェクト
 * @author Kodai Matsumoto
 */

abstract class X_Numeric extends X_Object {
    protected Number value;

    @Override
    X_Object and(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `&&` 演算子は使用できません");
    }

    @Override
    X_Object or(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `||` 演算子は使用できません");
    }
}
