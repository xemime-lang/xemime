package net.zero918nobita.Xemime.ast;

/**
 * 否定演算子
 * 評価されると、真偽(Bool)型の T は NIL に、NIL は T に変換します。
 * @author Kodai Matsumoto
 */

public class X_Not extends Node {
    private Node obj;

    public X_Not(int n, Node o) {
        super(n);
        obj = o;
    }

    @Override
    public Node run() throws Exception {
        Node o = obj.run();
        if (o.getClass() != X_Bool.class) throw new Exception(getLocation() + ": 真偽値以外のものには論理否定演算子を適用できません");
        X_Bool p = (X_Bool)o;
        if (p.isTrue()) return X_Bool.Nil;
        else return X_Bool.T;
    }
}
