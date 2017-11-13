package net.zero918nobita.Xemime;

/**
 * 簡易的なタプルの実装
 * @param <A> 一つ目の要素の型
 * @param <B> 二つ目の要素の型
 * @author Kodai Matsumoto
 */

public class Pair<A, B> {
    private final A left;
    private final B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }
}
