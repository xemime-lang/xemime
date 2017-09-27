package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.entity.Range;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;

/**
 * for 文を表すノードです。
 * @author Kodai Matsumoto
 */

public class ForNode extends Node {
    private Symbol counter;
    private Node range;
    private ArrayList<Node> body;

    public ForNode(int location, Symbol counter, Node range, ArrayList<Node> body) {
        super(location);
        this.counter = counter;
        this.range = range;
        this.body = body;
    }

    @Override
    public Node run() throws Exception {
        Node r = range.run();

        // Fatal Exception - for 文の範囲式として指定されたデータの型が不正です。
        if (!(r instanceof Range)) throw new FatalException(getLocation(), 41);

        int first = ((Range) r).getLeft();
        int second = ((Range) r).getRight();
        if (Main.hasSymbol(counter)) {
            Main.setValue(counter, new Int(0, first));
        } else {
            Main.defValue(counter, new Int(0, first));
        }
        for (int i = first; i <= second; i++) {
            for (Node node : body) node.run();
            Main.setValue(counter, new Int(0, i + 1));
        }
        return new Unit(getLocation(), null);
    }
}
