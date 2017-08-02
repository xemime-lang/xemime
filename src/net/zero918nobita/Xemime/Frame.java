package net.zero918nobita.Xemime;

import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;

/**
 * フレーム
 * シンボルとアドレスを対にして階層に分けて保持する。
 * @author Kodai Matsumoto
 */

class Frame {
    private Stack<HashMap<X_Symbol, X_Address>> localSymbols = new Stack<>();

    int numberOfLayers() {
        return localSymbols.size();
    }

    void loadLocalFrame(HashMap<X_Symbol, X_Address> table) {
        localSymbols.push(table);
    }

    void unloadLocalFrame() {
        localSymbols.pop();
    }

    boolean hasSymbol(X_Symbol sym) {
        if (localSymbols.size() != 0) {
            HashMap table = localSymbols.peek();
            if (table.containsKey(sym.getName())) return true;
        }
        return false;
    }

    X_Address getAddressOfSymbol(X_Symbol sym) {
        if (localSymbols.size() != 0) {
            HashMap<X_Symbol, X_Address> table = localSymbols.peek();
            if (table.containsKey(sym)) return table.get(sym);
        }
        return null;
    }

    X_Object getValueOfSymbol(X_Symbol sym, TreeMap<X_Address, X_Object> entities) {
        if (localSymbols.size() != 0) {
            HashMap<X_Symbol, X_Address> table = localSymbols.peek();
            if (table.containsKey(sym)) return table.get(sym).fetch(entities);
        }
        return null;
    }

    void setAddress(X_Symbol sym, X_Address ref) {
        if (localSymbols.size() != 0) {
            HashMap<X_Symbol, X_Address> table = localSymbols.peek();
            if (table.containsKey(sym)) table.put(sym, ref);
        }
    }

    void setValue(X_Symbol sym, X_Object obj) {
        if (localSymbols.size() != 0) {
            HashMap<X_Symbol, X_Address> table = localSymbols.peek();
            if (table.containsKey(sym)) {
                X_Address ref = Main.register(obj);
                table.put(sym, ref);
            }
        }
    }

    void defAddress(X_Symbol sym, X_Address ref) {
        HashMap<X_Symbol, X_Address> table = localSymbols.peek();
        table.put(sym, ref);
    }

    void defValue(X_Symbol sym, X_Object obj) {
        HashMap<X_Symbol, X_Address> table = localSymbols.peek();
        X_Address ref = Main.register(obj);
        table.put(sym, ref);
    }
}
