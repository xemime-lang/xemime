package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;

/**
 * 文字列オブジェクト
 * @author Kodai Matsumoto
 */

public class Str extends Handler {
    private final String value;

    public Str(int location, String str) {
        super(location);
        value = str;
        setMember(Symbol.intern(0, "length"), new X_Length());
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

    private class X_Length extends Native {
        X_Length() {
            super(0, 0);
        }

        @Override
        protected Int exec(ArrayList<Node> params, Address self) throws Exception {
            return new Int(0, (params.get(0)).toString().length());
        }
    }
}
