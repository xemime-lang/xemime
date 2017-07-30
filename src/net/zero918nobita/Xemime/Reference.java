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
    X_Object fetch(TreeMap<String, X_Object> entities) {
        return entities.get(String.valueOf(this.address));
    }
}
