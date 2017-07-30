package net.zero918nobita.Xemime;

/**
 * 真偽オブジェクト
 * @author Kodai Matsumoto
 */

class X_Bool extends X_Object {
    private boolean p;

    static X_Bool T;
    static X_Bool Nil;

    static {
        T = new X_Bool(true);
        Nil = new X_Bool(false);
    }

    X_Bool(boolean b) {
        p = b;
    }

    @Override
    public String toString() {
        return (p) ? "T" : "NIL";
    }
}
