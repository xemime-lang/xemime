package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * オブジェクト
 * メンバを管理する
 * @author Kodai Matsumoto
 */

public class Node {
    private int line;

    Node(int n) {
        line = n;
    }

    int getLocation() {
        return line;
    }

    public Node run() throws Exception {
        return this;
    }

    public Node add(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `+` 演算子は使用できません");
    }

    public Node sub(int line, Node obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `-` 演算子は使用できません");
    }

    public Node multiply(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `*` 演算子は使用できません");
    }

    public Node divide(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `/` 演算子は使用できません");
    }

    public X_Bool less(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `<` 演算子は使用できません");
    }

    public X_Bool le(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `<=` 演算子は使用できません");
    }

    public X_Bool greater(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `>` 演算子は使用できません");
    }

    public X_Bool ge(int line, Node obj) throws Exception {
        throw new Exception(line + ": このオブジェクトに `>=` 演算子は使用できません");
    }

    public X_Bool and(int line, Node obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `&&` 演算子は使用できません");
    }

    public X_Bool or(int line, Node obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `||` 演算子は使用できません");
    }

    public X_Bool xor(int line, Node obj) throws Exception {
        throw new Exception(line + "このオブジェクトに `^` 演算子は使用できません");
    }

    public Node message(int line, X_Symbol symbol) throws Exception {
        throw new Exception(line + ": このオブジェクトにフィールドは設定できません");
    }

    public Node message(int line, X_Symbol symbol, ArrayList<Node> params) throws Exception {
        throw new Exception(line + ": このオブジェクトにメソッドは設定できません");
    }
}
