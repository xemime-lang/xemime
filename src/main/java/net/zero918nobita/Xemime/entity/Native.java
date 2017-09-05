package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;

import java.util.ArrayList;

/**
 * 組み込みメソッド
 * 初めから用意されており、その挙動も Java コードで記述されているメソッドです。
 * @author Kodai Matsumoto
 */

public abstract class Native extends Function {

    public Native(int n, int args) {
        super(n);
        numberOfArgs = args;
    }

    public String toString() {
        return "<Native>";
    }

    @Override
    protected Node exec(ArrayList<Node> params, Address self) throws Exception {
        return null;
    }
}
