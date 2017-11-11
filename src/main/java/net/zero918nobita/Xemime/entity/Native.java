package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 組み込みメソッドを表すノードです。<br>
 * 組み込みメソッドとは、Xemime インタプリタ側で初めから用意している、
 * 挙動が Java コードで記述されたメソッドです。
 * @author Kodai Matsumoto
 */

public abstract class Native extends Func {

    public Native(int location, int args) {
        super(location);
        numberOfArgs = args;
    }

    public String toString() {
        return "<Native>";
    }

    protected Node exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
        return null;
    }

    protected Node exec(ArrayList<Node> params, Address self) throws Exception {
        return null;
    }
}
