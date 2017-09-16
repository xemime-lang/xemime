package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.interpreter.Frame;

import java.util.ArrayList;

public class Attr extends Closure {
    public Attr(int location, ArrayList<Symbol> params, Node node, Frame frame) {
        super(location, params, node, frame);
    }
}
