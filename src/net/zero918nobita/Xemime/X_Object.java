package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * オブジェクト
 * メンバを管理する
 * @author Kodai Matsumoto
 */

class X_Object {
    /**
     * メンバのリスト
     */
    private HashMap<X_Symbol, X_Object> members;

    X_Object() {
         members = new HashMap<>();
    }

    private boolean hasMember(X_Symbol symbol) {
        return members.containsKey(symbol);
    }

    /**
     * 新たにメンバを追加、または既存のメンバを上書きする
     * @param key メンバの名称
     * @param obj メンバの値
     */
    void setMember(X_Symbol key, X_Object obj) {
        members.put(key, obj);
    }

    /**
     * 既存のメンバの値を取得する
     * @param key メンバの名称
     * @return メンバの値
     */
    private X_Object getMember(X_Symbol key) {
        return members.get(key);
    }

    X_Object run() throws Exception {
        return this;
    }

    X_Object add(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `+` 演算子は使用できません");
    }

    X_Object sub(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `-` 演算子は使用できません");
    }

    X_Object multiply(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `*` 演算子は使用できません");
    }

    X_Object divide(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `/` 演算子は使用できません");
    }

    X_Bool less(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `<` 演算子は使用できません");
    }

    X_Bool le(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `<=` 演算子は使用できません");
    }

    X_Bool greater(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `>` 演算子は使用できません");
    }

    X_Bool ge(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `>=` 演算子は使用できません");
    }

    X_Bool and(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `&&` 演算子は使用できません");
    }

    X_Bool or(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `||` 演算子は使用できません");
    }

    X_Bool xor(X_Object obj) throws Exception {
        throw new Exception("このオブジェクトに `^` 演算子は使用できません");
    }

    X_Object message(X_Symbol symbol) throws Exception {
        if (!hasMember(symbol)) throw new Exception("`" + symbol.getName() + "` というフィールドはありません");
        return getMember(symbol);
    }

    X_Object message(X_Symbol symbol, ArrayList<X_Object> params) throws Exception {
        if (!hasMember(symbol)) throw new Exception("`" + symbol.getName() + "` というメソッドはありません");
        X_Object o = getMember(symbol);
        if (!(o instanceof X_Function)) throw new Exception("`" + symbol.getName() + "` はメソッドではありません");
        if (params == null) params = new ArrayList<>();
        params.add(0, this);
        return ((X_Function) o).call(params);
    }
}
