package net.zero918nobita.Xemime;

import java.util.ArrayList;

class X_Funcall extends X_Code {
    private X_Code func;
    private ArrayList<X_Code> list;

    X_Funcall(int n, X_Code code, ArrayList<X_Code> l) throws Exception {
        super(n);
        if (code instanceof X_Symbol || code instanceof X_Native) {
            func = code;
        } else {
            throw new Exception("深刻なエラー: 関数呼び出しに失敗しました");
        }
        list = l;
    }

    @Override
    X_Code run() throws Exception {
        if (func instanceof X_Native) {
            ArrayList<X_Code> params = new ArrayList<>();
            for (X_Code o : list) params.add(o.run());
            params.add(0, func);
            return ((X_Native) func).call(params);
        } else {
            X_Symbol symbol = (X_Symbol)func;
            X_Code c = Main.getValueOfSymbol(symbol);
            if (c == null) throw new Exception(getLocation() + ": 関数 `" + symbol.getName() + "` は存在しません");
            if (!(c instanceof X_Function)) throw new Exception(getLocation() + ": 変数 `" + symbol.getName() + "` には関数オブジェクトが代入されていません");
            X_Function func = (X_Function) c;
            ArrayList<X_Code> params = new ArrayList<>();
            for (X_Code o : list) params.add(o.run());
            params.add(0, func);
            return func.call(params);
        }
    }
}
