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

    @Override
    public boolean equals(Object object) {
        return object instanceof Pair && left.equals(((Pair) object).left) && right.equals(((Pair) object).right);
    }

    @Override
    public String toString() {
        return "Pair{" + left + ", " + right + "}";
    }

    public A getLeft() {
        return left;
    }

    public B getRight() {
        return right;
    }
}
