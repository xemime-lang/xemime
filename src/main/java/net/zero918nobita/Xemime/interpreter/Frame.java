package net.zero918nobita.Xemime.interpreter;

import net.zero918nobita.Xemime.entity.Address;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;

/**
 * フレーム
 * シンボルとアドレスを対にして階層に分けて保持します。
 * @author Kodai Matsumoto
 */

public class Frame {
    private ArrayList<Handler> localFrames = new ArrayList<>();

    public ArrayList<Handler> getLocalFrames() {
        return localFrames;
    }

    /** フレームの階層数を取得します。 */
    int numberOfLayers() {
        return localFrames.size();
    }

    /** フレームに新しい階層を追加します。 */
    void loadLocalFrame(Handler table) {
        localFrames.add(table);
        localFrames.get(localFrames.size() - 1).setMember(Symbol.intern("this"), Main.register(table));
    }

    /** 最後にフレームに追加された階層を破棄します。 */
    void unloadLocalFrame() {
        localFrames.remove(localFrames.size() - 1);
    }

    /** 全階層で指定されたシンボルが存在するかを調べ、存在する場合は true を、存在しない場合は false を返します。 */
    boolean hasSymbol(Symbol sym) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) return true;
        return false;
    }

    Address getAddressOfSymbol(Symbol sym) throws Exception {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) return localFrames.get(i).getAddressOfMember(sym);
        return null;
    }

    public Node getValueOfSymbol(Symbol sym) throws Exception {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) return localFrames.get(i).message(0, sym);
        return null;
    }

    void setAddress(Symbol sym, Address ref) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) localFrames.get(i).setMember(sym, ref);
    }

    void setValue(Symbol sym, Node obj) {
        if (localFrames.size() != 0)
            for (int i = localFrames.size() - 1; i > -1; i--)
                if (localFrames.get(i).hasMember(sym)) localFrames.get(i).setMember(sym, Main.register(obj));
    }

    void defAddress(Symbol sym, Address ref) throws Exception {
        if (localFrames.size() == 0) throw new Exception("深刻なエラー: フレームが存在しません");
        localFrames.get(localFrames.size() - 1).setMember(sym, ref);
    }

    public void defValue(Symbol sym, Node obj) throws Exception {
        if (localFrames.size() == 0) throw new Exception("深刻なエラー: フレームが存在しません");
        localFrames.get(localFrames.size() - 1).setMember(sym, Main.register(obj));
    }
}
