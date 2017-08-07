package net.zero918nobita.Xemime;

/**
 * 単項演算子 `-`
 * @author Kodai Matsumoto
 */

class X_Minus extends X_Code {
    private X_Code obj;

    X_Minus(X_Code o) {
        obj = o;
    }

    @Override
    X_Code run() throws Exception {
        X_Code o = obj.run();
        if (o.getClass() != X_Int.class && o.getClass() != X_Double.class)
            throw new Exception("数値以外のものには単項演算子を適用できません");
        if (o.getClass() == X_Int.class) return new X_Int(-((X_Int)o).getValue());
        return new X_Double(-((X_Double)o).getValue());
    }
}
