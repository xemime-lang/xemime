package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 名前付き引数リスト または 名前無し引数リスト を表すノードです。
 * @author Kodai Matsumoto
 */

public class Arguments {
    private LinkedHashMap<Symbol, Node> params;

    public Arguments(LinkedHashMap<Symbol, Node> params) {
        this.params = params;
    }

    public LinkedHashMap<Symbol, Node> getParams() {
        return params;
    }

    /**
     * 引数に渡された値を返します。名前付き引数リストの場合には定義したときの順番で指定してください。
     * @param index 指定する引数の、定義時の引数リストでの順番 ( 0 から始まる )
     * @return 値
     */
    public Node getParam(int index) {
        ArrayList<Node> args = new ArrayList<>();
        params.forEach((symbol, node) -> args.add(node));
        return args.get(index);
    }

    /**
     * 引数に渡された値を返します。
     * @param name 引数の名前
     * @return 値
     */
    public Node getParam(Symbol name) {
        return params.get(name);
    }
}
