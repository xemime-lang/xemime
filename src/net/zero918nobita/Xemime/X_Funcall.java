package net.zero918nobita.Xemime;

import java.util.ArrayList;

class X_Funcall extends X_Object {
    private X_Symbol symbol;
    private ArrayList<X_Object> list;

    X_Funcall(X_Symbol sym, ArrayList<X_Object> l) {
        symbol = sym;
        list = l;
    }

    @Override
    X_Object run() throws Exception {
        X_Object c = Main.getValueOfSymbol(symbol);
        if (c == null) throw new Exception("関数 `" + symbol.getName() + "` は存在しません");
        if (!(c instanceof X_Function)) throw new Exception("変数 `" + symbol.getName() + "` には関数オブジェクトが代入されていません");
        X_Function func = (X_Function)c;
        ArrayList<X_Object> evaluatedList = new ArrayList<>();
        for (X_Object o : list) evaluatedList.add(o.run());
        return func.call(evaluatedList);
    }
}
