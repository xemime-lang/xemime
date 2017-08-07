package net.zero918nobita.Xemime;

import java.util.ArrayList;

class X_DotCall extends X_Object {
    private X_Object obj;
    private X_Symbol symbol;
    private ArrayList<X_Object> list;

    X_DotCall(X_Object o, X_Symbol sym, ArrayList<X_Object> l) {
        obj = o;
        symbol = sym;
        list = l;
    }

    @Override
    X_Object run() throws Exception {
        X_Object o;
        o = obj.run();
        if (list != null) {
            ArrayList<X_Object> list2 = new ArrayList<>();
            for (X_Object arg : list) list2.add(arg.run());
            list = list2;
        }
        o = o.message(symbol, list);
        return o;
    }
}
