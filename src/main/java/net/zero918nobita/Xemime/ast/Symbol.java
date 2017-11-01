package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.entity.Address;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.HashMap;

/**
 * シンボルを表すノードです。
 * @author Kodai Matsumoto
 */

public class Symbol extends Node implements Comparable<Symbol> {
    private static HashMap<String, Symbol> table = new HashMap<>();
    private String name;

    public Symbol(int location, String name) {
        super(location);
        this.name = name;
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

    public static Symbol intern(int location, String s) {
        if (!table.containsKey(s)) table.put(s, new Symbol(location, s));
        return table.get(s);
    }

    public static Symbol intern(String s) {
        if (!table.containsKey(s)) table.put(s, new Symbol(0, s));
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

    public int compareTo(Symbol symbol) {
        return hashCode() - symbol.hashCode();
    }
}
