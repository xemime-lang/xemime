package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

/**
 * 文字列を表すノードです。
 * @author Kodai Matsumoto
 */

public class Str extends Node {
    private final String value;

    public Str(int location, String str) {
        super(location);
        value = str;
    }

    public Str(String str) {
        this(0, str);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Str) && (this.value.equals(obj.toString()));
    }

    @Override
    public Str add(int line, Node obj) throws Exception {
        if (obj instanceof Str) {
            return new Str(0, this.value + ((Str) obj).value);
        } else {
            throw new Exception(line + ": String型オブジェクトに他の型のオブジェクトを加算することはできません");
        }
    }
}
