package net.zero918nobita.Xemime;

/**
 * 単項演算子 `-`
 * @author Kodai Matsumoto
 */

class X_Minus extends X_Object {
    private X_Object obj;

    X_Minus(X_Object o) {
        obj = o;
    }

    @Override
    X_Object run(Environment env) throws Exception {
        X_Object o = obj.run(env);
        if (o.getClass() != X_Int.class || o.getClass() != X_Double.class)
            throw new Exception("数値以外のものには単項演算子を適用できません");
        if (o.getClass() == X_Int.class) return new X_Int(-((X_Int)o).getValue());
        return new X_Double(-((X_Double)o).getValue());
    }
}
