package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.interpreter.Main;

/**
 * オブジェクトのメンバを更新する代入演算子
 * @author Kodai Matsumoto
 */

public class X_DotAssign extends X_Code {
    private X_Code code1;
    private X_Symbol symbol;
    private X_Code code2;

    public X_DotAssign(int n, X_Code c, X_Symbol sym, X_Code c2) {
        super(n);
        code1 = c;
        symbol = sym;
        code2 = c2;
    }

    @Override
    public X_Code run() throws Exception {
        X_Code c1 = code1.run();
        X_Code c2 = code2.run();
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
