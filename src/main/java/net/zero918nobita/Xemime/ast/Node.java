package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Bool;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * ノードの原型です。<br>
 * 派生するノードがデフォルトで実装するフィールドやメソッドを定義しています。<br>
 * run メソッドで実行とその結果の取得ができ、各演算子に対する挙動も定義しています。
 * @author Kodai Matsumoto
 */

public class Node implements Recognizable {
    /** 行番号 */
    private int location;

    /**
     * ノードを生成します。
     * @param location 行番号
     */
    public Node(int location) {
        this.location = location;
    }

    @Override
    public NodeType recognize() {
        return NodeType.NODE;
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
     * このノードを左辺、渡されたノードを右辺として加算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 右辺
     * @return 和
     * @throws FatalException 常に例外を発生させます。
     */
    public Node add(int location, Node rhs) throws FatalException {
        // `+` 演算子は使用できません
        throw new FatalException(location,  98);
    }

    /**
     * 行番号を 0 として add メソッドを呼び出します。
     */
    public Node add(Node rhs) throws FatalException {
        return add(0, rhs);
    }


    /**
     * このノードを左辺、渡されたノードを右辺として減算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 右辺
     * @return 差
     * @throws FatalException 常に例外を発生させます。
     */
    public Node sub(int location, Node rhs) throws FatalException {
        // `-` 演算子は使用できません
        throw new FatalException(location, 99);
    }

    /**
     * 行番号を 0 として sub メソッドを呼び出します。
     */
    public Node sub(Node rhs) throws FatalException {
        return sub(0, rhs);
    }

    /**
     * このノードを左辺、渡されたノードを右辺として乗算を行います。
     * @param location 演算を行う行の行番号(行番号)
     * @param rhs 右辺
     * @return 積
     * @throws FatalException 常に例外を発生させます。
     */
    public Node multiply(int location, Node rhs) throws FatalException {
        // `*` 演算子は使用できません
        throw new FatalException(location,  100);
    }

    /**
     * 行番号を 0 として multiply メソッドを呼び出します。
     */
    public Node multiply(Node rhs) throws FatalException {
        return multiply(0, rhs);
    }

    /**
     * このノードを左辺、渡されたノードを右辺として除算を行います。
     * @param location 演算を行う行の行番号
     * @param rhs 右辺
     * @return 商
     * @throws FatalException 常に例外を発生させます。
     */
    public Node divide(int location, Node rhs) throws FatalException {
        // `/` 演算子は使用できません
        throw new FatalException(location, 101);
    }

    /**
     * 行番号を 0 として divide メソッドを呼び出します。
     */
    public Node divide(Node rhs) throws FatalException {
        return divide(0, rhs);
    }

    /**
     * 大小を比較して、このノードが比較対象「より小さい」場合に真値を返します。
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
     * 行番号を 0 として less メソッドを呼び出します。
     */
    public Bool less(Node rhs) throws FatalException {
        return less(0, rhs);
    }

    /**
     * 大小を比較して、このノードが比較対象「以下」の場合に真値を返します。
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
     * 行番号を 0 として le メソッドを呼び出します。
     */
    public Bool le(Node rhs) throws FatalException {
        return le(0, rhs);
    }

    /**
     * 大小を比較して、このノードが比較対象「より大きい」場合に真値を返します。
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
     * 行番号を 0 として greater メソッドを呼び出します。
     */
    public Bool greater(Node rhs) throws FatalException {
        return greater(0, rhs);
    }

    /**
     * 大小を比較して、このノードが比較対象「以上」の場合に真値を返します。
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
     * 行番号を 0 として ge メソッドを呼び出します。
     */

    public Bool ge(Node rhs) throws FatalException {
        return ge(0, rhs);
    }

    /**
     * このノードを左辺、渡されたノードを右辺として論理積を求めます。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 論理積(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool and(int location, Node rhs) throws FatalException {
        // `&&` 演算子は使用できません。
        throw new FatalException(location, 114);
    }

    /**
     * 行番号を 0 として and メソッドを呼び出します。
     */
    public Bool and(Node rhs) throws FatalException {
        return and(0, rhs);
    }

    /**
     * このノードを左辺、渡されたノードを右辺として論理和を求めます。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 論理和(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool or(int location, Node rhs) throws FatalException {
        // `||` 演算子は使用できません。
        throw new FatalException(location, 115);
    }

    /**
     * 行番号を 0 として or メソッドを呼び出します。
     */
    public Bool or(Node rhs) throws FatalException {
        return or(0, rhs);
    }

    /**
     * このノードを左辺、渡されたノードを右辺として排他的論理和を求めます。
     * @param location 論理演算を行う行の行番号
     * @param rhs 右辺
     * @return 排他的論理和(真偽値)
     * @throws FatalException 常に例外を発生させます。
     */
    public Bool xor(int location, Node rhs) throws FatalException {
        // `^` 演算子は使用できません。
        throw new FatalException(location, 116);
    }

    /**
     * 行番号を 0 として xor メソッドを呼び出します。
     */
    public Bool xor(Node rhs) throws FatalException {
        return xor(0, rhs);
    }

    /**
     * このノードの指定したプロパティの値を取得します。
     * @param location プロパティ参照を行う行の行番号
     * @param symbol プロパティ名
     * @return プロパティの値
     * @throws FatalException 常に例外を発生させます。
     */
    public Node message(int location, Symbol symbol) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にフィールドは設定できません");
    }

    /**
     * このノードの指定したメソッドを名前付き引数で呼び出し、戻り値を取得します。
     * @param location メソッドを呼び出す行の行番号
     * @param symbol メソッド名
     * @param params 名前付き引数
     * @return メソッドを呼び出して得られた戻り値
     * @throws FatalException 常に例外を発生させます。
     */
    public Node message(int location, Symbol symbol, LinkedHashMap<Symbol, Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドは設定できません");
    }

    /**
     * このノードの指定したメソッドを名前なし引数で呼び出し、戻り値を取得します。
     * @param location メソッドを呼び出す行の行番号
     * @param symbol メソッド名
     * @param params 名前なし引数
     * @return メソッドを呼び出して得られた戻り値
     * @throws FatalException 常に例外を発生させます。
     */
    public Node message(int location, Symbol symbol, ArrayList<Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドを設定できません");
    }
}
