package net.zero918nobita.Xemime;

import java.util.Hashtable;

/**
 * シンボル
 * @author Kodai Matsumoto
 */

class X_Symbol extends X_Object {
    private static Hashtable<String, X_Symbol> table = new Hashtable<>();
    private String name;

    X_Symbol(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    static X_Object intern(String s) {
        if (table.containsKey(s)) return table.get(s);
        else return new X_Symbol(s);
    }

    public X_Object run(Environment env) throws Exception {
        X_Object c = env.getInterpreter().getValueOfSymbol(this);
        if (c == null) throw new Exception("シンボル" + name + "は定義されていません");
        return c;
    }

    public String toString() {
        return name;
    }
}
