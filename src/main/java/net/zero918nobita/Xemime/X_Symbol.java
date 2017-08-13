package net.zero918nobita.Xemime;

import java.util.HashMap;

/**
 * シンボル
 * @author Kodai Matsumoto
 */

class X_Symbol extends X_Code {
    private static HashMap<String, X_Symbol> table = new HashMap<>();
    private String name;

    X_Symbol(int n, String s) {
        super(n);
        name = s;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof X_Symbol && ((X_Symbol)obj).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    static X_Symbol intern(int n, String s) {
        if (!table.containsKey(s)) table.put(s, new X_Symbol(n, s));
        return table.get(s);
    }

    public X_Code run() throws Exception {
        X_Code c = Main.getValueOfSymbol(this);
        if (c == null) throw new Exception(getLocation() + ": シンボル `" + name + "` は定義されていません");
        return c;
    }

    public String toString() {
        return name;
    }
}
