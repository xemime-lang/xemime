package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.ast.Node;

/**
 * 真偽値
 * @author Kodai Matsumoto
 */

public class Bool extends Node {
    private boolean bool;

    public static Bool T = new Bool(true);
    public static Bool Nil = new Bool(false);

    public Bool(int location, boolean bool) {
        super(location);
        this.bool = bool;
    }

    @Override
    public NodeType recognize() {
        return NodeType.BOOL;
    }

    public Bool(boolean bool) {
        this(0, bool);
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

    /**
     * この真偽値を左辺、渡されたノードを右辺として論理積を求めます。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 論理積(真偽値)
     * @throws FatalException 右辺が真偽値ではない場合に例外を発生させます。
     */
    @Override
    public Bool and(int location, Node rhs) throws FatalException {
        if (rhs instanceof Bool) return (bool && (((Bool)rhs).isTrue())) ? Bool.T : Bool.Nil;
        else throw new FatalException(location,  117);
    }

    /**
     * この真偽値を左辺、渡されたノードを右辺として論理和を求めます。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 論理和(真偽値)
     * @throws FatalException 右辺が真偽値でない場合に例外を発生させます。
     */
    @Override
    public Bool or(int location, Node rhs) throws FatalException {
        if (rhs instanceof Bool) return (bool || (((Bool) rhs).isTrue())) ? Bool.T : Bool.Nil;
        else throw new FatalException(location, 118);
    }

    /**
     * この真偽値を左辺、渡されたノードを右辺として排他的論理和を求めます。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 排他的論理和(真偽値)
     * @throws FatalException 右辺が真偽値でない場合に例外を発生させます。
     */
    @Override
    public Bool xor(int location, Node rhs) throws FatalException {
        if (rhs instanceof Bool) return (bool ^ (((Bool) rhs).isTrue())) ? Bool.T : Bool.Nil;
        else throw new FatalException(location, 119);
    }
}
