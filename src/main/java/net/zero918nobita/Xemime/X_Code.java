package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * オブジェクト
 * メンバを管理する
 * @author Kodai Matsumoto
 */

class X_Code {
    private int line;

    X_Code(int n) {
        line = n;
    }

    int getLocation() {
        return line;
    }

    X_Code run() throws Exception {
        return this;
    }

    X_Code add(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `+` 演算子は使用できません");
    }

    X_Code sub(int line, X_Code obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `-` 演算子は使用できません");
    }

    X_Code multiply(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `*` 演算子は使用できません");
    }

    X_Code divide(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `/` 演算子は使用できません");
    }

    X_Bool less(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `<` 演算子は使用できません");
    }

    X_Bool le(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `<=` 演算子は使用できません");
    }

    X_Bool greater(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `>` 演算子は使用できません");
    }

    X_Bool ge(int line, X_Code obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `>=` 演算子は使用できません");
    }

    X_Bool and(int line, X_Code obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `&&` 演算子は使用できません");
    }

    X_Bool or(int line, X_Code obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `||` 演算子は使用できません");
    }

    X_Bool xor(int line, X_Code obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `^` 演算子は使用できません");
    }

    X_Code message(int line, X_Symbol symbol) throws Exception {
        throw new Exception("このオブジェクトにフィールドは設定されていません");
    }

    X_Code message(int line, X_Symbol symbol, ArrayList<X_Code> params) throws Exception {
        throw new Exception("このオブジェクトにメソッドは設定されていません");
    }
}
