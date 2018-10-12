package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.ast.Node;

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

    @Override
    public Bool and(int location, Node rhs) throws FatalException {
        if (rhs instanceof Bool) return (bool && (((Bool)rhs).isTrue())) ? Bool.T : Bool.Nil;
        else throw new FatalException(location,  117);
    }

    @Override
    public Bool or(int location, Node rhs) throws FatalException {
        if (rhs instanceof Bool) return (bool || (((Bool) rhs).isTrue())) ? Bool.T : Bool.Nil;
        else throw new FatalException(location, 118);
    }

    @Override
    public Bool xor(int location, Node rhs) throws FatalException {
        if (rhs instanceof Bool) return (bool ^ (((Bool) rhs).isTrue())) ? Bool.T : Bool.Nil;
        else throw new FatalException(location, 119);
    }
}
