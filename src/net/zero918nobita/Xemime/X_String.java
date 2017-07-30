package net.zero918nobita.Xemime;

/**
 * 文字列クラス
 * @author Kodai Matsumoto
 */

class X_String extends X_Object {
    private final String value;

    X_String(String str) {
        value = str;
    }

    @Override
    public String toString() {
        return value;
    }
}
