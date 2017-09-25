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
        if (node != null) {
            node = node.run();
        } else {
            throw new FatalException(getLocation(), 36);
        }
        if (node instanceof Int) {
            node.add(getLocation(), one);
        } else {
            throw new FatalException(getLocation(), 37);
        }
        return node;
    }
}
