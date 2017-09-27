package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.interpreter.Main;

/**
 * 前置インクリメントを表すコードです。
 * @author Kodai Matsumoto
 */

public class PrefixIncrementNode extends Node {
    private Symbol symbol;
    private static Int one = new Int(0, 1);

    public PrefixIncrementNode(int location, Symbol symbol) {
        super(location);
        this.symbol = symbol;
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
