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
        if (node != null) {
            node = node.run();
        } else {
            throw new FatalException(getLocation(), 34);
        }
        if (node instanceof Int) {
            Main.setValue(symbol, node.sub(getLocation(), one));
        } else {
            throw new FatalException(getLocation(), 35);
        }
        return Main.getValueOfSymbol(symbol);
    }
}
