package net.zero918nobita.Xemime.ast;

/**
 * 単項演算子 `-`
 * 評価されると、数値(Int, Double)型の値の符号を反転させます。
 * @author Kodai Matsumoto
 */

public class X_Minus extends Node {
    private Node obj;

    public X_Minus(int n, Node o) {
        super(n);
        obj = o;
    }

    @Override
    public Node run() throws Exception {
        Node o = obj.run();
        if (o.getClass() != X_Int.class && o.getClass() != X_Double.class)
            throw new Exception(getLocation() + ": 数値以外のものには単項演算子を適用できません");
        if (o.getClass() == X_Int.class) return new X_Int(getLocation(), -((X_Int)o).getValue());
        return new X_Double(getLocation(), -((X_Double)o).getValue());
    }
}
