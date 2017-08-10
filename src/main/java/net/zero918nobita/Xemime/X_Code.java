package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * オブジェクト
 * メンバを管理する
 * @author Kodai Matsumoto
 */

class X_Code {
    X_Code run() throws Exception {
        return this;
    }

    X_Code add(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `+` 演算子は使用できません");
    }

    X_Code sub(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `-` 演算子は使用できません");
    }

    X_Code multiply(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `*` 演算子は使用できません");
    }

    X_Code divide(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `/` 演算子は使用できません");
    }

    X_Bool less(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `<` 演算子は使用できません");
    }

    X_Bool le(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `<=` 演算子は使用できません");
    }

    X_Bool greater(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `>` 演算子は使用できません");
    }

    X_Bool ge(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `>=` 演算子は使用できません");
    }

    X_Bool and(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `&&` 演算子は使用できません");
    }

    X_Bool or(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `||` 演算子は使用できません");
    }

    X_Bool xor(X_Code obj) throws Exception {
        throw new Exception("このオブジェクトに `^` 演算子は使用できません");
    }

    X_Code message(X_Symbol symbol) throws Exception {
        throw new Exception("このオブジェクトにフィールドは設定されていません");
    }

    X_Code message(X_Symbol symbol, ArrayList<X_Code> params) throws Exception {
        throw new Exception("このオブジェクトにメソッドは設定されていません");
    }
}
