package net.zero918nobita.Xemime;

import java.util.TreeMap;

class X_Address implements Comparable {
    private int address;

    X_Address(int n) {
        address = n;
    }

    int getAddress() {
        return address;
    }

    X_Object fetch(TreeMap<X_Address, X_Object> entities) {
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
