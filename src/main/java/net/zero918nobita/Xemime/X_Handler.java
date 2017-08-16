package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.HashMap;

class X_Handler extends X_Code {
    /**
     * メンバのリスト
     */
    private HashMap<X_Symbol, X_Address> members;

    X_Handler(int n) {
        super(n);
        members = new HashMap<>();
    }

    boolean hasMember(X_Symbol symbol) {
        return members.containsKey(symbol);
    }

    /**
     * 新たにメンバを追加、または既存のメンバを上書きする
     * @param key メンバの名称
     * @param obj メンバの値
     */
    void setMember(X_Symbol key, X_Code obj) {
        if (obj instanceof X_Address) {
            members.put(key, (X_Address) obj);
        } else {
            members.put(key, Main.register(obj));
        }
    }

    /**
     * 既存のメンバの値を取得する
     * @param key メンバの名称
     * @return メンバの値
     */
    X_Code getMember(X_Symbol key) {
        return Main.getValueOfReference(members.get(key));
    }

    X_Address getAddressOfMember(X_Symbol key) {
        return members.get(key);
    }

    @Override
    X_Code message(int line, X_Symbol symbol) throws Exception {
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というフィールドはありません");
        return getMember(symbol);
    }

    @Override
    X_Code message(int line, X_Symbol symbol, ArrayList<X_Code> params) throws Exception {
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というメソッドはありません");
        X_Code o = getMember(symbol);
        if (!(o instanceof X_Function)) throw new Exception(line + ": `" + symbol.getName() + "` はメソッドではありません");
        if (params == null) params = new ArrayList<>();
        params.add(0, this);
        return ((X_Function) o).call(params, Main.register(this));
    }
}
