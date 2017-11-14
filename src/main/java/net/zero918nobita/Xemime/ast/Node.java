package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Bool;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
     * @throws FatalException 常に例外を発生させます。
     */
    public Node add(int location, Node rhs) throws FatalException {
        // `+` 演算子は使用できません
        throw new FatalException(location,  98);
    }

    /**
     * 加算を行います(行番号の指定を省略)。
     * @param rhs 足す数
     * @return 和
     * @throws FatalException 常に例外を発生させます。
     */
    public Node add(Node rhs) throws FatalException {
        return add(0, rhs);
    }


    /**
     * 減算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 引く数
     * @return 差
     * @throws FatalException 常に例外を発生させます。
     */
    public Node sub(int location, Node rhs) throws FatalException {
        // `-` 演算子は使用できません
        throw new FatalException(location, 99);
    }

    /**
     * 減算を行います(行番号の指定を省略)。
     * @param rhs 足す数
     * @return 和
     * @throws FatalException 常に例外を発生させます。
     */
    public Node sub(Node rhs) throws FatalException {
        return sub(0, rhs);
    }

    /**
     * 乗算を行います。
     * @param location 演算を行う行の行番号(行番号)
     * @param rhs 掛ける数
     * @return 積
     * @throws FatalException 常に例外を発生させます。
     */
    public Node multiply(int location, Node rhs) throws FatalException {
        // `*` 演算子は使用できません
        throw new FatalException(location,  100);
    }

    /**
     * 乗算を行います(行番号の指定を省略)。
     * @param rhs 掛ける数
     * @return 積
     * @throws FatalException 常に例外を発生させます。
     */
    public Node multiply(Node rhs) throws FatalException {
        return multiply(0, rhs);
    }

    /**
     * 除算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 割る数
     * @return 商
     * @throws FatalException 常に例外を発生させます。
     */
    public Node divide(int location, Node rhs) throws FatalException {
        // `/` 演算子は使用できません
        throw new FatalException(location, 101);
    }

    /**
     * 除算を行います(行番号の指定を省略)。
     * @param rhs 割る数
     * @return 商
     * @throws FatalException 常に例外を発生させます。
     */
    public Node divide(Node rhs) throws FatalException {
        return divide(0, rhs);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「より小さい」場合に真値を返します。
     * @param location 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool less(int location, Node rhs) throws FatalException {
        // `<` 演算子は使用できません
        throw new FatalException(location, 102);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「より小さい」場合に真値を返します(行番号の指定を省略)。
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool less(Node rhs) throws FatalException {
        return less(0, rhs);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「以下」の場合に真値を返します。
     * @param location 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool le(int location, Node rhs) throws FatalException {
        // `<=` 演算子は使用できません
        throw new FatalException(location, 103);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「以下」の場合に真値を返します(行番号の指定を省略)。
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool le(Node rhs) throws FatalException {
        return le(0, rhs);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「より大きい」場合に真値を返します。
     * @param location 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool greater(int location, Node rhs) throws FatalException {
        // `>` 演算子は使用できません。
        throw new FatalException(location, 104);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「より大きい」場合に真値を返します(行番号の指定を省略)。
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool greater(Node rhs) throws FatalException {
        return greater(0, rhs);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「以上」の場合に真値を返します。
     * @param location 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool ge(int location, Node rhs) throws FatalException {
        // `>=` 演算子は使用できません。
        throw new FatalException(location, 105);
    }

    /**
     * 大小を比較して、このオブジェクトが比較対象「以上」の場合に真値を返します。
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 常に例外を発生させます。
     */

    public Bool ge(Node rhs) throws FatalException {
        return ge(0, rhs);
    }

    /**
     * 論理積を返します。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 論理積(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool and(int location, Node rhs) throws FatalException {
        // `&&` 演算子は使用できません。
        throw new FatalException(location, 106);
    }

    /**
     * 論理積を返します(行番号の指定を省略)。
     * @param rhs 右辺
     * @return 論理積(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool and(Node rhs) throws FatalException {
        return and(0, rhs);
    }

    /**
     * 論理和を返します。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 論理和(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool or(int location, Node rhs) throws FatalException {
        throw new FatalException(location, 107);
    }

    /**
     * 論理和を返します(行番号の指定を省略)。
     * @param rhs 右辺
     * @return 論理和(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool or(Node rhs) throws FatalException {
        return or(0, rhs);
    }

    public Bool xor(int location, Node rhs) throws Exception {
        throw new Exception(location + "`" + toString() + "` に `^` 演算子は使用できません");
    }

    public Bool xor(Node rhs) throws Exception {
        return xor(0, rhs);
    }

    public Node message(int location, Symbol symbol) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にフィールドは設定できません");
    }

    public Node message(int location, Symbol symbol, LinkedHashMap<Symbol, Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドは設定できません");
    }

    public Node message(int location, Symbol symbol, ArrayList<Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドを設定できません");
    }
}
