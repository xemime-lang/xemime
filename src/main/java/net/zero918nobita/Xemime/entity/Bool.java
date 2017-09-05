package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * 真偽オブジェクト
 * @author Kodai Matsumoto
 */

public class Bool extends Handler {
    private boolean bool;

    public static Bool T = new Bool(0,true);
    public static Bool Nil = new Bool(0,false);

    public Bool(int location, boolean bool) {
        super(location);
        this.bool = bool;
    }

    @Override
    public String toString() {
        return (bool) ? "T" : "NIL";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Bool) && (((Bool)obj).isTrue() == this.bool);
    }

    public boolean isTrue() {
        return bool;
    }

    @Override
    public Node add(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値の加算はできません");
    }

    @Override
    public Node sub(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値の減算はできません");
    }

    @Override
    public Node multiply(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値の乗算はできません");
    }

    @Override
    public Node divide(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値の除算はできません");
    }

    @Override
    public Bool less(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値に `<` 演算子は使用できません");
    }

    @Override
    public Bool le(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値に `<=` 演算子は使用できません");
    }

    @Override
    public Bool greater(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値に `>` 演算子は使用できません");
    }

    @Override
    public Bool ge(int location, Node node) throws Exception {
        throw new Exception(location + ": 真偽値に `>=` 演算子は使用できません");
    }

    @Override
    public Bool and(int location, Node node) throws Exception {
        if (node instanceof Bool) return (bool && (((Bool)node).isTrue())) ? Bool.T : Bool.Nil;
        else throw new Exception(location + ": `&&` 演算子の右辺が真偽値ではありません");
    }

    @Override
    public Bool or(int location, Node node) throws Exception {
        if (node instanceof Bool) return (bool || (((Bool) node).isTrue())) ? Bool.T : Bool.Nil;
        else throw new Exception(location + ": `||` 演算子の右辺が真偽値ではありません");
    }

    @Override
    public Bool xor(int location, Node node) throws Exception {
        if (node instanceof Bool) return (bool ^ (((Bool) node).isTrue())) ? Bool.T : Bool.Nil;
        else throw new Exception(location + ": `^` 演算子の右辺が真偽値ではありません");
    }
}
