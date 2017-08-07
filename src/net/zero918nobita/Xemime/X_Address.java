package net.zero918nobita.Xemime;

import java.util.TreeMap;

/**
 * アドレス
 * グローバルフレーム、ローカルフレームで管理され、シンボルとその値にあたる実体を結びつける。
 * フレームの値であり、実体テーブルのキーでもある。
 * @author Kodai Matsumoto
 */

class X_Address extends X_Code implements Comparable {
    private int address;

    X_Address(int n) {
        address = n;
    }

    int getAddress() {
        return address;
    }

    X_Code fetch(TreeMap<X_Address, X_Code> entities) {
        return entities.get(this);
    }

    @Override
    public String toString() {
        return "<#" + String.valueOf(address) + ">";
    }

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
