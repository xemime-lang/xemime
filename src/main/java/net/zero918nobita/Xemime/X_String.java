package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * 文字列オブジェクト
 * @author Kodai Matsumoto
 */

class X_String extends X_Handler {
    private final String value;

    X_String(String str) {
        value = str;
        setMember(new X_Symbol("length"), new X_Length());
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
    X_String add(X_Code obj) throws Exception {
        if (obj instanceof X_String) {
            return new X_String(this.value + ((X_String) obj).value);
        } else {
            throw new Exception("String型オブジェクトに他の型のオブジェクトを加算することはできません");
        }
    }

    private class X_Length extends X_Native {
        X_Length() {
            super(0);
        }

        @Override
        protected X_Int exec(ArrayList<X_Code> params) throws Exception {
            return new X_Int((params.get(0)).toString().length());
        }
    }
}
