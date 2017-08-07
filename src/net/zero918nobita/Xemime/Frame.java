package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * フレーム
 * シンボルとアドレスを対にして階層に分けて保持する。
 * @author Kodai Matsumoto
 */

class Frame {
    private ArrayList<HashMap<X_Symbol, X_Address>> localFrames = new ArrayList<>();

    int numberOfLayers() {
        return localFrames.size();
    }

    void loadLocalFrame(HashMap<X_Symbol, X_Address> table) {
        localFrames.add(table);
    }

    void unloadLocalFrame() {
        localFrames.remove(localFrames.size() - 1);
    }

    boolean hasSymbol(X_Symbol sym) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).containsKey(sym)) return true;
        return false;
    }

    X_Address getAddressOfSymbol(X_Symbol sym) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).containsKey(sym)) return localFrames.get(i).get(sym);
        return null;
    }

    X_Code getValueOfSymbol(X_Symbol sym, TreeMap<X_Address, X_Code> entities) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).containsKey(sym)) return localFrames.get(i).get(sym).fetch(entities);
        return null;
    }

    void setAddress(X_Symbol sym, X_Address ref) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).containsKey(sym)) localFrames.get(i).put(sym, ref);
    }

    void setValue(X_Symbol sym, X_Code obj) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).containsKey(sym)) localFrames.get(i).put(sym, Main.register(obj));
    }

    void defAddress(X_Symbol sym, X_Address ref) {
        localFrames.get(localFrames.size() - 1).put(sym, ref);
    }

    void defValue(X_Symbol sym, X_Code obj) {
        localFrames.get(localFrames.size() - 1).put(sym, Main.register(obj));
    }
}
