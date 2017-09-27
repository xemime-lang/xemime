package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.interpreter.Main;

/**
 * 前置デクリメントを表すノードです。
 * @author Kodai Matsumoto
 */

public class PrefixDecrementNode extends Node {
    private Symbol symbol;
    private static Int one = new Int(0, 1);

    public PrefixDecrementNode(int location, Symbol symbol) {
        super(location);
        this.symbol = symbol;
    }

    @Override
    public Node run() throws Exception {
        Node node = Main.getValueOfSymbol(symbol);

        // Fatal Exception - 前置デクリメントの評価に失敗しました。
        if (node == null) throw new FatalException(getLocation(), 34);

        node = node.run();

        // Fatal Exception - 前置デクリメントの評価に失敗しました。
        if (!(node instanceof Int)) throw new FatalException(getLocation(), 35);

        Main.setValue(symbol, node.sub(getLocation(), one));
        return Main.getValueOfSymbol(symbol);
    }
}
