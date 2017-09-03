package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;
import java.util.HashMap;

public class X_Handler extends X_Code {
    /**
     * メンバのリスト
     */
    private HashMap<X_Symbol, X_Address> members;

    public X_Handler(int n) {
        super(n);
        members = new HashMap<>();
    }

    public boolean hasMember(X_Symbol symbol) {
        return members.containsKey(symbol);
    }

    /**
     * 新たにメンバを追加、または既存のメンバを上書きする
     * @param key メンバの名称
     * @param obj メンバの値
     */
    public void setMember(X_Symbol key, X_Code obj) {
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
    public X_Code getMember(X_Symbol key) {
        return Main.getValueOfReference(members.get(key));
    }

    public HashMap<X_Symbol, X_Address> getMembers() throws Exception {
        return members;
    }

    public X_Address getAddressOfMember(X_Symbol key) {
        return members.get(key);
    }

    @Override
    public X_Code message(int line, X_Symbol symbol) throws Exception {
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というフィールドはありません");
        return getMember(symbol);
    }

    @Override
    public X_Code message(int line, X_Symbol symbol, ArrayList<X_Code> params) throws Exception {
        if (symbol.equals(X_Symbol.intern(0, "proto")))
            throw new Exception(line + ": protoフィールドはメソッドとして呼び出すことはできません");
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というメソッドはありません");
        X_Code o = getMember(symbol);
        if (!(o instanceof X_Function)) throw new Exception(line + ": `" + symbol.getName() + "` はメソッドではありません");
        if (params == null) params = new ArrayList<>();
        params.add(0, this);
        return ((X_Function) o).call(params, Main.register(this));
    }
}
