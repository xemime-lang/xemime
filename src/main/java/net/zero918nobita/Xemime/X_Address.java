package net.zero918nobita.Xemime;

import java.util.TreeMap;

/**
 * アドレス
 * グローバルフレーム、ローカルフレームで管理され、シンボルとその値にあたる実体を結びつけます。
 * フレームの値であり、実体テーブルのキーでもあります。
 * @author Kodai Matsumoto
 */

class X_Address extends X_Code implements Comparable {
    private int address;

    X_Address(int n, int a) {
        super(n);
        address = a;
    }

    /** アドレスを取得します。 */
    int getAddress() {
        return address;
    }

    /** 参照先の値を取得します。 */
    X_Code fetch(TreeMap<X_Address, X_Code> entities) {
        return entities.get(this);
    }

    /** アドレスを文字列化します。 */
    @Override
    public String toString() {
        return "<#" + String.valueOf(address) + ">";
    }

    /** 参照先が一致していれば true 、そうでなければ false を返します。 */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof X_Address && address == ((X_Address)obj).getAddress();
    }

    @Override
    public int hashCode() {
        return address;
    }

    @Override
    public int compareTo(Object obj) {
        return address - ((X_Address)obj).getAddress();
    }
}
