package net.zero918nobita.Xemime;

import java.util.ArrayList;

abstract class X_Native extends X_Function {

    X_Native(int n) {
        numberOfArgs = n;
    }

    public String toString() {
        return "<Native>";
    }

    @Override
    protected X_Code exec(ArrayList<X_Code> params) throws Exception {
        return null;
    }
}
