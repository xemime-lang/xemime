package net.zero918nobita.Xemime;

/**
 * 文字列クラス
 * @author Kodai Matsumoto
 */

class X_String extends X_Handler {
    private final String value;

    X_String(String str) {
        value = str;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof X_String) && (this.value.equals(obj.toString()));
    }

    @Override
    X_String add(X_Code obj) throws Exception {
        if (obj instanceof X_String) {
            return new X_String(this.value + ((X_String) obj).value);
        } else {
            throw new Exception("String型オブジェクトに他の型のオブジェクトを加算することはできません");
        }
    }
}
