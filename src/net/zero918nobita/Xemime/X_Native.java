package net.zero918nobita.Xemime;

abstract class X_Native extends X_Function {

    X_Native(int n) {
        numberOfArgs = n;
    }

    public String toString() {
        return "<Native>";
    }
}
