package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Address;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.HashMap;

/**
 * シンボルを表すノードです。
 * @author Kodai Matsumoto
 */

public class Symbol extends Node {
    private static HashMap<String, Symbol> table = new HashMap<>();
    private String name;

    public Symbol(int n, String s) {
        super(n);
        name = s;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Symbol && ((Symbol)obj).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public static Symbol intern(int n, String s) {
        if (!table.containsKey(s)) table.put(s, new Symbol(n, s));
        return table.get(s);
    }

    public Node run() throws Exception {
        Node c = Main.getValueOfSymbol(this);
        if (c == null) throw new Exception(getLocation() + ": シンボル `" + name + "` は定義されていません");
        return c;
    }

    Address getAddress() throws Exception {
        return Main.getAddressOfSymbol(this);
    }

    public String toString() {
        return name;
    }
}
