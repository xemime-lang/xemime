package net.zero918nobita.Xemime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * フレーム
 * シンボルとアドレスを対にして階層に分けて保持します。
 * @author Kodai Matsumoto
 */

class Frame {
    private ArrayList<X_Handler> localFrames = new ArrayList<>();

    /** フレームの階層数を取得します。 */
    int numberOfLayers() {
        return localFrames.size();
    }

    /** フレームに新しい階層を追加します。 */
    void loadLocalFrame(X_Handler table) {
        localFrames.add(table);
        localFrames.get(localFrames.size() - 1).setMember(X_Symbol.intern(0, "this"), Main.register(table));
    }

    /** 最後にフレームに追加された階層を破棄します。 */
    void unloadLocalFrame() {
        localFrames.remove(localFrames.size() - 1);
    }

    /** 全階層で指定されたシンボルが存在するかを調べ、存在する場合は true を、存在しない場合は false を返します。 */
    boolean hasSymbol(X_Symbol sym) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) return true;
        return false;
    }

    X_Address getAddressOfSymbol(X_Symbol sym) throws Exception {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) return (X_Address) localFrames.get(i).message(0, sym);
        return null;
    }

    X_Code getValueOfSymbol(X_Symbol sym) throws Exception {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) return localFrames.get(i).message(0, sym);
        return null;
    }

    void setAddress(X_Symbol sym, X_Address ref) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) localFrames.get(i).setMember(sym, ref);
    }

    void setValue(X_Symbol sym, X_Code obj) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) localFrames.get(i).setMember(sym, Main.register(obj));
    }

    void defAddress(X_Symbol sym, X_Address ref) throws Exception {
        if (localFrames.size() == 0) throw new Exception("深刻なエラー: フレームが存在しません");
        localFrames.get(localFrames.size() - 1).setMember(sym, ref);
    }

    void defValue(X_Symbol sym, X_Code obj) throws Exception {
        if (localFrames.size() == 0) throw new Exception("深刻なエラー: フレームが存在しません");
        localFrames.get(localFrames.size() - 1).setMember(sym, Main.register(obj));
    }
}
