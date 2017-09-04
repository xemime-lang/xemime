package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * オブジェクトのメソッドの呼び出し
 * @author Kodai Matsumoto
 */

public class DotCallNode extends X_Code {
    private X_Code obj;
    private X_Symbol symbol;
    private ArrayList<X_Code> list;

    public DotCallNode(int n, X_Code o, X_Symbol sym, ArrayList<X_Code> l) {
        super(n);
        obj = o;
        symbol = sym;
        list = l;
    }

    @Override
    public X_Code run() throws Exception {
        X_Code o;
        o = obj.run();
        if (list != null) {
            ArrayList<X_Code> list2 = new ArrayList<>();
            for (X_Code arg : list) list2.add(arg.run());
            list = list2;
        }
        o = o.message(getLocation(), symbol, list);
        return o;
    }
}
