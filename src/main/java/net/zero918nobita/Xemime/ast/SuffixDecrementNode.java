package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.interpreter.Main;

/**
 * 後置デクリメントを表すノードです。
 * @author Kodai Matsumotopackage net.zero918nobita.Xemime.ast;
 */

public class SuffixDecrementNode extends Node implements Recognizable {
    private Symbol symbol;
    private static Int one = new Int(0, 1);

    public SuffixDecrementNode(int location, Symbol symbol) {
        super(location);
        this.symbol = symbol;
    }

    @Override
    public NodeType recognize() {
        return NodeType.SUFFIX_DEC;
    }

    @Override
    public Node run() throws Exception {
        Node node = Main.getValueOfSymbol(symbol);

        // FatalException - 後置デクリメントの評価に失敗しました。
        if (node == null) throw new FatalException(getLocation(), 26);

        node = node.run();

        // FatalException - 後置デクリメントの評価に失敗しました。
        if (!(node instanceof Int)) throw new FatalException(getLocation(), 28);

        Main.setValue(symbol, node.sub(getLocation(), one));
        return node;
    }
}
