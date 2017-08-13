package net.zero918nobita.Xemime;

/**
 * 真偽オブジェクト
 * @author Kodai Matsumoto
 */

class X_Bool extends X_Handler {
    private boolean p;

    static X_Bool T;
    static X_Bool Nil;

    static {
        T = new X_Bool(0,true);
        Nil = new X_Bool(0,false);
    }

    private X_Bool(int n, boolean b) {
        super(n);
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
    X_Code add(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値の加算はできません");
    }

    @Override
    X_Code sub(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値の減算はできません");
    }

    @Override
    X_Code multiply(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値の乗算はできません");
    }

    @Override
    X_Code divide(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値の除算はできません");
    }

    @Override
    X_Bool less(int line, X_Code obj) throws Exception {
        throw new Exception(line + "真偽値に `<` 演算子は使用できません");
    }

    @Override
    X_Bool le(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値に `<=` 演算子は使用できません");
    }

    @Override
    X_Bool greater(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値に `>` 演算子は使用できません");
    }

    @Override
    X_Bool ge(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": 真偽値に `>=` 演算子は使用できません");
    }

    @Override
    X_Bool and(int line, X_Code obj) throws Exception {
        if (obj instanceof X_Bool) return (p && (((X_Bool)obj).isTrue())) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": `&&` 演算子の右辺が真偽値ではありません");
    }

    @Override
    X_Bool or(int line, X_Code obj) throws Exception {
        if (obj instanceof X_Bool) return (p || (((X_Bool) obj).isTrue())) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": `||` 演算子の右辺が真偽値ではありません");
    }

    @Override
    X_Bool xor(int line, X_Code obj) throws Exception {
        if (obj instanceof X_Bool) return (p ^ (((X_Bool) obj).isTrue())) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": `^` 演算子の右辺が真偽値ではありません");
    }
}
