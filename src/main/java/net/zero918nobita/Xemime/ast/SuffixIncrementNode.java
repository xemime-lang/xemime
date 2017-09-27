package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.interpreter.Main;

/**
 * 後置インクリメントを表すノードです。
 * @author Kodai Matsumoto
 */

public class SuffixIncrementNode extends Node {
    private Symbol symbol;
    private static Int one = new Int(0, 1);

    public SuffixIncrementNode(int location, Symbol symbol) {
        super(location);
        this.symbol = symbol;
    }

    @Override
    public Node run() throws Exception {
        Node node = Main.getValueOfSymbol(symbol);

        // FatalException - 後置インクリメントの評価に失敗しました。
        if (node == null) throw new FatalException(getLocation(), 32);

        node = node.run();

        // FatalException - 後置インクリメントの評価に失敗しました。
        if (!(node instanceof Int)) throw new FatalException(getLocation(), 33);

        Main.setValue(symbol, node.add(getLocation(), one));
        return node;
    }
}
