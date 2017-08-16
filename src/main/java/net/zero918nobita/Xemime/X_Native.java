package net.zero918nobita.Xemime;

import java.util.ArrayList;

abstract class X_Native extends X_Function {

    X_Native(int n, int args) {
        super(n);
        numberOfArgs = args;
    }

    public String toString() {
        return "<Native>";
    }

    @Override
    protected X_Code exec(ArrayList<X_Code> params, X_Address self) throws Exception {
        return null;
    }
}
