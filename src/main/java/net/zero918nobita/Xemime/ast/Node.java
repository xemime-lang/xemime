package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Bool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * ノードの原型です。<br>
 * 派生するノードがデフォルトで実装するフィールドやメソッドを定義しています。<br>
 * run メソッドで実行とその結果の取得ができ、各演算子に対する挙動も定義しています。
 * @author Kodai Matsumoto
 */

public class Node {
    /** 行番号 */
    private int location;

    /**
     * ノードを生成します。
     * @param location 行番号
     */
    public Node(int location) {
        this.location = location;
    }

    /**
     * 行番号を取得します。
     * @return 行番号
     */
    public int getLocation() {
        return this.location;
    }

    /**
     * 実行します。
     * @return ノード自体を返します。
     * @throws Exception 何らかの理由で実行に失敗した場合に例外を発生させます。
     */
    public Node run() throws Exception {
        return this;
    }

    /**
     * 加算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 足す数
     * @return 和
     * @throws Exception 常に例外を発生させます。
     */
    public Node add(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `+` 演算子は使用できません");
    }

    public Node add(Node rhs) throws Exception {
        return add(0, rhs);
    }


    /**
     * 減算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 引く数
     * @return 差
     * @throws Exception 常に例外を発生させます。
     */
    public Node sub(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `-` 演算子は使用できません");
    }

    public Node sub(Node rhs) throws Exception {
        return sub(0, rhs);
    }

    /**
     * 乗算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 掛ける数
     * @return 積
     * @throws Exception 常に例外を発生させます。
     */
    public Node multiply(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `*` 演算子は使用できません");
    }

    public Node multiply(Node rhs) throws Exception {
        return multiply(0, rhs);
    }

    /**
     * 除算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 割る数
     * @return 商
     * @throws Exception 常に例外を発生させます。
     */
    public Node divide(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `/` 演算子は使用できません");
    }

    public Node divide(Node rhs) throws Exception {
        return divide(0, rhs);
    }

    public Bool less(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `<` 演算子は使用できません");
    }

    public Bool less(Node rhs) throws Exception {
        return less(0, rhs);
    }

    public Bool le(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `<=` 演算子は使用できません");
    }

    public Bool le(Node rhs) throws Exception {
        return le(0, rhs);
    }

    public Bool greater(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `>` 演算子は使用できません");
    }

    public Bool greater(Node rhs) throws Exception {
        return greater(0, rhs);
    }

    public Bool ge(int location, Node rhs) throws Exception {
        throw new Exception(location + ": `" + toString() + "` に `>=` 演算子は使用できません");
    }

    public Bool and(int location, Node rhs) throws Exception {
        throw new Exception(location + "`" + toString() + "` に `&&` 演算子は使用できません");
    }

    public Bool or(int location, Node rhs) throws Exception {
        throw new Exception(location + "`" + toString() + "` に `||` 演算子は使用できません");
    }

    public Bool xor(int location, Node rhs) throws Exception {
        throw new Exception(location + "`" + toString() + "` に `^` 演算子は使用できません");
    }

    public Node message(int location, Symbol symbol) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にフィールドは設定できません");
    }

    public Node message(int location, Symbol symbol, LinkedHashMap<Symbol, Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドは設定できません");
    }
}
