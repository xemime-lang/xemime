package net.zero918nobita.Xemime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * エントリーポイント
 * Xemime の対話的実行環境
 * @author Kodai Matsumoto
 */

public class Main {
    private static HashMap<X_Symbol, X_Address> globalSymbols = new HashMap<>();
    private static TreeMap<X_Address, X_Object> entities = new TreeMap<>();
    private static Frame frame = new Frame();

    static void loadLocalFrame(HashMap<X_Symbol, X_Address> table) {
        frame.loadLocalFrame(table);
    }

    static void unloadLocalFrame() {
        frame.unloadLocalFrame();
    }

    static boolean hasSymbol(X_Symbol sym) {
        return frame.hasSymbol(sym) || globalSymbols.containsKey(sym);
    }

    /**
     * シンボルの参照先アドレスを取得する
     * @param sym シンボル
     * @return 参照
     */
    static X_Address getAddressOfSymbol(X_Symbol sym) {
        return (frame.hasSymbol(sym)) ?
                frame.getAddressOfSymbol(sym) :
                globalSymbols.get(sym);
    }

    /**
     * シンボルの値を取得する
     * @param sym シンボル
     * @return 値
     */
    static X_Object getValueOfSymbol(X_Symbol sym) {
        return (frame.hasSymbol(sym)) ?
                frame.getValueOfSymbol(sym, entities) :
                globalSymbols.get(sym).fetch(entities);
    }

    /**
     * シンボルに参照をセットする
     * @param sym シンボル
     * @param ref 参照
     */
    static void setAddress(X_Symbol sym, X_Address ref) {
        if (frame.hasSymbol(sym)) { frame.setAddress(sym, ref); return; }
        globalSymbols.put(sym, ref);
    }

    /**
     * 変数に値をセットする
     * @param sym シンボル
     * @param obj 値
     */
    static void setValue(X_Symbol sym, X_Object obj) {
        if (frame.hasSymbol(sym)) { frame.setValue(sym, obj); return; }
        X_Address ref = register(obj);
        globalSymbols.put(sym, ref);
    }

    /**
     * 変数を参照で定義する
     * @param sym 変数
     * @param ref 参照
     */
    static void defAddress(X_Symbol sym, X_Address ref) {
        if (frame.numberOfLayers() != 0) { frame.defAddress(sym, ref); return; }
        globalSymbols.put(sym, ref);
    }

    /**
     * 変数を値で定義する
     * @param sym 変数
     * @param obj 値
     */
    static void defValue(X_Symbol sym, X_Object obj) {
        if (frame.numberOfLayers() != 0) { frame.defValue(sym, obj); return; }
        X_Address ref = register(obj);
        globalSymbols.put(sym, ref);
    }

    static X_Address register(X_Object obj) {
        entities.put(new X_Address(entities.lastKey().getAddress() + 1), obj);
        return new X_Address(entities.lastKey().getAddress());
    }

    public static void main(String[] args) {
        entities.put(new X_Address(0), X_Bool.T);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Lexer lex = new Lexer(in);
            Parser parser = new Parser();
            while (true) {
                System.out.print("Prelude> ");
                X_Object obj = parser.parse(lex);
                if (obj == null) break;
                System.out.println(obj.run().toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
