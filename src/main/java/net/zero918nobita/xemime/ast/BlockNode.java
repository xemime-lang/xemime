package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Handler;
import net.zero918nobita.xemime.interpreter.Main;

import java.util.ArrayList;

/**
 * ブロック式を表すノードです。<br>
 * 戻り値を持つため、ブロック内に何も記述していない場合例外を発生させます。
 * @author Kodai Matsumoto
 */

public class BlockNode extends Node implements Recognizable {
    private ArrayList<Node> list;

    public BlockNode(int location, ArrayList<Node> list) {
        super(location);
        this.list = list;
    }

    @Override
    public NodeType recognize() {
        return NodeType.BLOCK;
    }

    @Override
    public Node run() throws Exception {
        Node obj = null;
        Main.loadLocalFrame(new Handler(0));
        if (list != null) for (Node o : list) obj = o.run();

        // Fatal Exception - ブロック式の戻り値が設定されていません。
        if (obj == null) throw new FatalException(getLocation(), 24);

        Main.unloadLocalFrame();
        return obj;
    }
}
