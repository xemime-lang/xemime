package net.zero918nobita.Xemime;

import java.util.TreeMap;

/**
 * 参照
 * @author Kodai Matsumoto
 */

class Reference {
    private int address;
    Reference(int address) {
        this.address = address;
    }
    X_Object fetch(TreeMap<X_Address, X_Object> entities) {
        return entities.get(new X_Address(address));
    }
}
