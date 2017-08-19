package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.Map;

class X_Closure extends X_Function {
    private ArrayList<X_Symbol> params;
    private X_Address self = null;
    private X_Code body;

    /**
     * 捕捉変数テーブル<br>
     * 捕捉変数(ラムダ式が使用する変数)
     */
    private Frame captured = null;

    X_Closure(int n, ArrayList<X_Symbol> l, X_Code obj, Frame frame) {
        super(n);
        params = l;
        body = obj;
        captured = frame;
        if (params != null) numberOfArgs = params.size();
    }

    void setSelf(X_Address self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "<Lambda>";
    }

    @Override
    X_Code run() throws Exception {
        return this;
    }

    @Override
    protected X_Code exec(ArrayList<X_Code> params, X_Address dynamicSelf) throws Exception {
        X_Code o = null;
        setArgs(params, dynamicSelf);
        if (body != null) o = body.run();
        removeArgs();
        return o;
    }

    private void setArgs(ArrayList<X_Code> args, X_Address dynamicSelf) throws Exception {
        if ((params == null) && (args == null)) {
            Main.loadLocalFrame(new X_Handler(0));
            return;
        }

        X_Handler table = new X_Handler(getLocation());
        Main.loadLocalFrame(table);
        if (captured != null)
            for (X_Handler o : captured.getLocalFrames()) {
                for (Map.Entry<X_Symbol, X_Address> entry : o.getMembers().entrySet()) {
                    System.out.println(entry.getKey());
                    table.setMember(entry.getKey(), entry.getValue());
                }
            }

        if (self != null) {
            table.setMember(X_Symbol.intern(getLocation(), "THIS"), Main.register(self));
        } else {
            table.setMember(X_Symbol.intern(getLocation(), "THIS"), Main.defaultObj);
        }
        if (dynamicSelf != null) table.setMember(X_Symbol.intern(getLocation(), "this"), Main.register(dynamicSelf));

        for (int i = 0; i < args.size() - 1; i++) {
            X_Symbol sym = (X_Symbol)params.get(i);
            X_Code o = args.get(i + 1);
            table.setMember(sym, Main.register(o));
        }
    }

    private void removeArgs() throws Exception {
        Main.unloadLocalFrame();
    }
}
