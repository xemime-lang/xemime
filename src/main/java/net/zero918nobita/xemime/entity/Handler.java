package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.ast.Node;
import net.zero918nobita.xemime.ast.Symbol;
import net.zero918nobita.xemime.interpreter.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Handler extends Node {
    /**
     * メンバのリスト
     */
    private HashMap<Symbol, Address> members;

    public Handler(int n) {
        super(n);
        members = new HashMap<>();
    }

    public boolean hasMember(Symbol symbol) {
        return members.containsKey(symbol);
    }

    /**
     * 新たにメンバを追加、または既存のメンバを上書きする
     * @param key メンバの名称
     * @param obj メンバの値
     */
    public void setMember(Symbol key, Node obj) {
        if (obj instanceof Address) {
            members.put(key, (Address) obj);
        } else {
            members.put(key, Main.register(obj));
        }
    }

    /**
     * 既存のメンバの値を取得する
     * @param key メンバの名称
     * @return メンバの値
     */
    public Node getMember(Symbol key) {
        return Main.getValueOfReference(members.get(key));
    }

    public HashMap<Symbol, Address> getMembers() throws Exception {
        return members;
    }

    public Address getAddressOfMember(Symbol key) {
        return members.get(key);
    }

    @Override
    public Node message(int line, Symbol symbol) throws Exception {
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というフィールドはありません");
        return getMember(symbol);
    }

    @Override
    public Node message(int line, Symbol symbol, LinkedHashMap<Symbol, Node> params) throws Exception {
        if (symbol.equals(Symbol.intern("proto")))
            throw new Exception(line + ": proto フィールドはメソッドとして呼び出すことはできません");
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というメソッドはありません");
        Node o = getMember(symbol);
        if (!(o instanceof Func)) throw new Exception(line + ": `" + symbol.getName() + "` はメソッドではありません");
        if (params == null) params = new LinkedHashMap<>();
        params.put(Symbol.intern("this"), this);
        return ((Func) o).call(getLocation(), params, Main.register(this));
    }

    @Override
    public Node message(int line, Symbol symbol, ArrayList<Node> params) throws Exception {
        if (symbol.equals(Symbol.intern("proto")))
            throw new Exception(line + ": proto フィールドはメソッドとして呼び出すことはできません");
        if (!hasMember(symbol)) throw new Exception(line + ": `" + symbol.getName() + "` というメソッドはありません");
        Node o = getMember(symbol);
        if (!(o instanceof Func)) throw new Exception(line + ": `" + symbol.getName() + "` はメソッドではありません");
        if (params == null) params = new ArrayList<>();
        params.add(0, this);
        return ((Func) o).call(getLocation(), params, Main.register(this));
    }
}
