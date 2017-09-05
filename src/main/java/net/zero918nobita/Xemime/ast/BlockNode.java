package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * ブロック
 * 戻り値を持ちます。
 * そのためブロック内に何も記述していない場合例外を発生させます。
 * @author Kodai Matsumoto
 */

public class BlockNode extends Node {
    private ArrayList<Node> list;

    public BlockNode(int n, ArrayList<Node> l) {
        super(n);
        list = l;
    }

    @Override
    public Node run() throws Exception {
        Node obj = null;
        if (list != null) for (Node o : list) obj = o.run();
        if (obj == null) throw new Exception(getLocation() + ": ブロックの戻り値が記述されていません");
        return obj;
    }
}
