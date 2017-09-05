package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;

/**
 * オブジェクトのメンバを更新する代入演算子
 * @author Kodai Matsumoto
 */

public class DotAssignNode extends Node {
    private Node code1;
    private X_Symbol symbol;
    private Node code2;

    public DotAssignNode(int n, Node c, X_Symbol sym, Node c2) {
        super(n);
        code1 = c;
        symbol = sym;
        code2 = c2;
    }

    @Override
    public Node run() throws Exception {
        Node c1 = code1.run();
        Node c2 = code2.run();
        if (c1 instanceof X_Handler) {
            if (c2 instanceof X_Address) {
                ((X_Handler) c1).setMember(symbol, c2);
            } else {
                ((X_Handler) c1).setMember(symbol, Main.register(c2));
            }
        } else {
            throw new Exception(getLocation() + ": メンバを設定することができません");
        }
        return c2;
    }
}
