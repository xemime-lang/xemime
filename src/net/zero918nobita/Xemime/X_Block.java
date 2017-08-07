package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ブロック
 * Common Lisp の progn のようにブロック自体が戻り値を持つ
 * そのためブロック内に何も記述していない場合例外を投げる
 * @author Kodai Matsumoto
 */

class X_Block extends X_Object {
    private ArrayList<X_Object> list;

    X_Block(ArrayList<X_Object> l) {
        list = l;
    }

    @Override
    X_Object run() throws Exception {
        Main.loadLocalFrame(new HashMap<>());
        X_Object obj = null;
        if (list != null) for (X_Object o : list) obj = o.run();
        if (obj == null) throw new Exception("ブロックの戻り値が記述されていません");
        Main.unloadLocalFrame();
        return obj;
    }
}
