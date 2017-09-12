package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Int;

/**
 * 単項演算子 `-` を表すノードです。<br>
 * 評価されると、数値(Int, Double)型の値の符号を反転させます。
 * @author Kodai Matsumoto
 */

public class MinusNode extends Node {
    private Node obj;

    public MinusNode(int n, Node o) {
        super(n);
        obj = o;
    }

    @Override
    public Node run() throws Exception {
        Node o = obj.run();
        if (o.getClass() != Int.class && o.getClass() != Double.class)
            throw new Exception(getLocation() + ": 数値以外のものには単項演算子を適用できません");
        if (o.getClass() == Int.class) return new Int(getLocation(), -((Int)o).getValue());
        return new Double(getLocation(), -((Double)o).getValue());
    }
}
