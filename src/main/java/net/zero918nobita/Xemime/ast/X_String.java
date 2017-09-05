package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * 文字列オブジェクト
 * @author Kodai Matsumoto
 */

public class X_String extends X_Handler {
    private final String value;

    public X_String(int n, String str) {
        super(n);
        value = str;
        setMember(X_Symbol.intern(0, "length"), new X_Length());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof X_String) && (this.value.equals(obj.toString()));
    }

    @Override
    public X_String add(int line, Node obj) throws Exception {
        if (obj instanceof X_String) {
            return new X_String(0, this.value + ((X_String) obj).value);
        } else {
            throw new Exception(line + ": String型オブジェクトに他の型のオブジェクトを加算することはできません");
        }
    }

    private class X_Length extends X_Native {
        X_Length() {
            super(0, 0);
        }

        @Override
        protected X_Int exec(ArrayList<Node> params, X_Address self) throws Exception {
            return new X_Int(0, (params.get(0)).toString().length());
        }
    }
}
