package net.zero918nobita.Xemime.entity;

/**
 * 数値オブジェクト
 * @author Kodai Matsumoto
 */

abstract class Numeric extends Handler {
    protected Number value;

    Numeric(int n) {
        super(n);
    }
}
