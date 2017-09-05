package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Bool;

/**
 * 否定演算子
 * 評価されると、真偽(Bool)型の T は NIL に、NIL は T に変換します。
 * @author Kodai Matsumoto
 */

public class NotNode extends Node {
    private Node obj;

    public NotNode(int n, Node o) {
        super(n);
        obj = o;
    }

    @Override
    public Node run() throws Exception {
        Node o = obj.run();
        if (o.getClass() != Bool.class) throw new Exception(getLocation() + ": 真偽値以外のものには論理否定演算子を適用できません");
        Bool p = (Bool)o;
        if (p.isTrue()) return Bool.Nil;
        else return Bool.T;
    }
}
