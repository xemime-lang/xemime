package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * オブジェクトのメソッドの呼び出し
 * @author Kodai Matsumoto
 */

class X_DotCall extends X_Code {
    private X_Code obj;
    private X_Symbol symbol;
    private ArrayList<X_Code> list;

    X_DotCall(X_Code o, X_Symbol sym, ArrayList<X_Code> l) {
        obj = o;
        symbol = sym;
        list = l;
    }

    @Override
    X_Code run() throws Exception {
        X_Code o;
        o = obj.run();
        if (list != null) {
            ArrayList<X_Code> list2 = new ArrayList<>();
            for (X_Code arg : list) list2.add(arg.run());
            list = list2;
        }
        o = o.message(symbol, list);
        return o;
    }
}
