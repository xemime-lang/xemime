package net.zero918nobita.xemime.ast;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.entity.Int;
import net.zero918nobita.xemime.interpreter.Main;

/**
 * 前置インクリメントを表すコードです。
 * @author Kodai Matsumoto
 */

public class PrefixIncrementNode extends Node implements Recognizable {
    private Symbol symbol;
    private static Int one = new Int(0, 1);

    public PrefixIncrementNode(int location, Symbol symbol) {
        super(location);
        this.symbol = symbol;
    }

    @Override
    public NodeType recognize() {
        return NodeType.PREFIX_INC;
    }

    @Override
    public Node run() throws Exception {
        Node node = Main.getValueOfSymbol(symbol);

        // Fatal Exception - 前置インクリメントの評価に失敗しました。
        if (node == null) throw new FatalException(getLocation(), 36);

        node = node.run();

        // Fatal Exception - 前置インクリメントの評価に失敗しました。
        if (!(node instanceof Int)) throw new FatalException(getLocation(), 37);

        Main.setValue(symbol, node.add(getLocation(), one));
        return Main.getValueOfSymbol(symbol);
    }
}
