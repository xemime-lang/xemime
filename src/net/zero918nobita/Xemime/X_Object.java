package net.zero918nobita.Xemime;

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
    private HashMap<String, X_Object> members;

    X_Object() {
         members = new HashMap<>();
    }

    /**
     * 新たにメンバを追加、または既存のメンバを上書きする
     * @param key メンバの名称
     * @param obj メンバの値
     */
    void setMember(String key, X_Object obj) {
        members.put(key, obj);
    }

    /**
     * 既存のメンバの値を取得する
     * @param key メンバの名称
     * @return メンバの値
     */
    X_Object getMember(String key) {
        return members.get(key);
    }

    X_Object run(Environment env) throws Exception {
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
}
