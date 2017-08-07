package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.HashMap;

class X_Lambda extends X_Function {
    private ArrayList<X_Code> params;
    private X_Code body;

    X_Lambda(ArrayList<X_Code> l, X_Code obj) {
        params = l;
        body = obj;
        if (params != null) numberOfArgs = params.size();
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
    protected X_Code exec(ArrayList<X_Code> params) throws Exception {
        X_Code o = null;
        setArgs(params);
        if (body != null) o = body.run();
        removeArgs();
        return o;
    }

    private void setArgs(ArrayList<X_Code> args) throws Exception {
        if ((params == null) && (args == null)) {
            Main.loadLocalFrame(new HashMap<>());
            return;
        }

        HashMap<X_Symbol, X_Address> table = new HashMap<>();
        Main.loadLocalFrame(table);

        for (int i = 0; i < args.size(); i++) {
            X_Symbol sym = (X_Symbol)params.get(i);
            X_Code o = args.get(i);
            table.put(sym, Main.register(o));
        }
    }

    private void removeArgs() throws Exception {
        Main.unloadLocalFrame();
    }
}
