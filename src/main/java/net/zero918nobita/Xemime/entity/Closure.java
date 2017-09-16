package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.interpreter.Frame;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;
import java.util.Map;

/**
 * クロージャ
 * @author Kodai Matsumoto
 */

public class Closure extends Function {
    protected ArrayList<Symbol> params;
    protected Address self = null;
    protected Node body;

    /**
     * 捕捉変数テーブル<br>
     * 捕捉変数(ラムダ式が使用する変数)
     */
    protected Frame captured = null; // Main との連携も考えると、HashMap<Symbol, Address> にするかも…

    public Closure(int location, ArrayList<Symbol> l, Node obj, Frame frame) {
        super(location);
        params = l;
        body = obj;
        captured = frame;
        if (params != null) numberOfArgs = params.size();
    }

    void setSelf(Address self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "<Lambda>";
    }

    @Override
    public Node run() throws Exception {
        return this;
    }

    @Override
    protected Node exec(ArrayList<Node> params, Address dynamicSelf) throws Exception {
        Node o = null;
        setArgs(params, dynamicSelf);
        if (body != null) o = body.run();
        removeArgs();
        return o;
    }

    protected void setArgs(ArrayList<Node> args, Address dynamicSelf) throws Exception {
        if ((params == null) && (args == null)) {
            Main.loadLocalFrame(new Handler(0));
            return;
        }

        Handler table = new Handler(getLocation());
        Main.loadLocalFrame(table);
        if (captured != null)
            for (Handler o : captured.getLocalFrames()) {
                for (Map.Entry<Symbol, Address> entry : o.getMembers().entrySet()) {
                    table.setMember(entry.getKey(), entry.getValue());
                }
            }

        if (self != null) {
            table.setMember(Symbol.intern(getLocation(), "THIS"), Main.register(self));
        } else {
            table.setMember(Symbol.intern(getLocation(), "THIS"), Main.defaultObj);
        }
        if (dynamicSelf != null) table.setMember(Symbol.intern(getLocation(), "this"), Main.register(dynamicSelf));

        for (int i = 0; i < args.size() - 1; i++) {
            Symbol sym = (Symbol)params.get(i);
            Node o = args.get(i + 1);
            table.setMember(sym, Main.register(o));
        }
    }

    protected void removeArgs() throws Exception {
        Main.unloadLocalFrame();
    }
}
