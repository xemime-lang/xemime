package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * 真偽オブジェクト
 * @author Kodai Matsumoto
 */

public class Bool extends Node {
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
