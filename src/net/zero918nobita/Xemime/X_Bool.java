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

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof X_Bool) && (((X_Bool)obj).isTrue() == this.p);
    }

    boolean isTrue() {
        return p;
    }

    @Override
    X_Object add(X_Object obj) throws Exception {
        throw new Exception("真偽値の加算はできません");
    }

    @Override
    X_Object sub(X_Object obj) throws Exception {
        throw new Exception("真偽値の減算はできません");
    }

    @Override
    X_Object multiply(X_Object obj) throws Exception {
        throw new Exception("真偽値の乗算はできません");
    }

    @Override
    X_Object divide(X_Object obj) throws Exception {
        throw new Exception("真偽値の除算はできません");
    }

    @Override
    X_Object less(X_Object obj) throws Exception {
        throw new Exception("真偽値に `<` 演算子は使用できません");
    }

    @Override
    X_Object le(X_Object obj) throws Exception {
        throw new Exception("真偽値に `<=` 演算子は使用できません");
    }

    @Override
    X_Object greater(X_Object obj) throws Exception {
        throw new Exception("真偽値に `>` 演算子は使用できません");
    }

    @Override
    X_Object ge(X_Object obj) throws Exception {
        throw new Exception("真偽値に `>=` 演算子は使用できません");
    }

    @Override
    X_Object and(X_Object obj) throws Exception {
        if (obj instanceof X_Bool) return (p && (((X_Bool)obj).isTrue())) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("`&&` 演算子の右辺が真偽値ではありません");
    }

    @Override
    X_Object or(X_Object obj) throws Exception {
        if (obj instanceof X_Bool) return (p || (((X_Bool) obj).isTrue())) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("`||` 演算子の右辺が真偽値ではありません");
    }
}
