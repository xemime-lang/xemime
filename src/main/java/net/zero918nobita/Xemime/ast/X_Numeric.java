package net.zero918nobita.Xemime.ast;

/**
 * 数値オブジェクト
 * @author Kodai Matsumoto
 */

abstract class X_Numeric extends X_Handler {
    protected Number value;

    X_Numeric(int n) {
        super(n);
    }
}
