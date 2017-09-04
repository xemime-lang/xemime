package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.ast.X_Code;

import java.util.ArrayList;

/**
 * ブロック
 * 戻り値を持ちます。
 * そのためブロック内に何も記述していない場合例外を発生させます。
 * @author Kodai Matsumoto
 */

public class X_Block extends X_Code {
    private ArrayList<X_Code> list;

    public X_Block(int n, ArrayList<X_Code> l) {
        super(n);
        list = l;
    }

    @Override
    public X_Code run() throws Exception {
        X_Code obj = null;
        if (list != null) for (X_Code o : list) obj = o.run();
        if (obj == null) throw new Exception(getLocation() + ": ブロックの戻り値が記述されていません");
        return obj;
    }
}
