package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Bool;
import org.jetbrains.annotations.NotNull;

/** 論理否定演算子 */
public class NotNode extends Node implements Recognizable {
    private Node node;

    public NotNode(int location, Node node) {
        super(location);
        this.node = node;
    }

    @Override
    @NotNull
    public NodeType recognize() {
        return NodeType.NOT;
    }

    @Override
    @NotNull
    public Node run() throws Exception {
        Node o = node.run();

        // Fatal Exception - 真偽値以外のデータには単項演算子 `!` を適用できません
        if (o.getClass() != Bool.class) throw new FatalException(getLocation(), 15);

        Bool p = (Bool)o;
        if (p.equals(Bool.getT())) return Bool.getNil();
        else return Bool.getT();
    }
}
