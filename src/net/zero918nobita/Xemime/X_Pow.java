package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * pow 関数
 * これは、関数呼び出しが正常に行われているかテストするために
 * 実験的に実装している組み込み関数です。
 */

class X_Pow extends X_Native {

    X_Pow() {
        super(2);
    }

    protected X_Object exec(ArrayList<X_Object> params) throws Exception {
        X_Object b = params.get(0);
        X_Object e = params.get(1);
        if (!(b instanceof X_Numeric && e instanceof X_Numeric))
            throw new Exception("引数は数値でなければなりません");
        X_Double base = (b instanceof X_Double) ? (X_Double)b : ((X_Int)b).to_d();
        X_Double exponent = (e instanceof X_Double) ? (X_Double)e : ((X_Int)e).to_d();
        return new X_Double(Math.pow(base.getValue(), exponent.getValue()));
    }
}
