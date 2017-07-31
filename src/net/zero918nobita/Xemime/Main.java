package net.zero918nobita.Xemime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeMap;

public class Main {
    private static HashMap<X_Symbol, Reference> Globals = new HashMap<>();
    private static TreeMap<X_Address, X_Object> entities = new TreeMap<>();
    private static Frame frame = new Frame();

    static void pushLocals(HashMap<X_Symbol, Reference> table) {
        frame.pushLocals(table);
    }

    static void popLocals() {
        frame.popLocals();
    }

    static boolean hasSymbol(X_Symbol sym) {
        return frame.hasSymbol(sym) || Globals.containsKey(sym);
    }

    /**
     * シンボルの参照を取得する
     * @param sym シンボル
     * @return 参照
     */
    static Reference getReferenceOfSymbol(X_Symbol sym) {
        return (frame.hasSymbol(sym)) ? frame.getReferenceOfSymbol(sym) : Globals.get(sym);
    }

    /**
     * シンボルの値を取得する
     * @param sym シンボル
     * @return 値
     */
    static X_Object getValueOfSymbol(X_Symbol sym) {
        return (frame.hasSymbol(sym)) ? frame.getValueOfSymbol(sym, entities) : Globals.get(sym).fetch(entities);
    }

    /**
     * シンボルに参照をセットする
     * @param sym シンボル
     * @param ref 参照
     */
    static void setReference(X_Symbol sym, Reference ref) {
        if (frame.hasSymbol(sym)) { frame.setReference(sym, ref); return; }
        Globals.put(sym, ref);
    }

    /**
     * 変数に値をセットする
     * @param sym シンボル
     * @param obj 値
     */
    static void setValue(X_Symbol sym, X_Object obj) {
        if (frame.hasSymbol(sym)) { frame.setValue(sym, obj); return; }
        Reference ref = register(obj);
        Globals.put(sym, ref);
    }

    /**
     * 変数を参照で定義する
     * @param sym 変数
     * @param ref 参照
     */
    static void defReference(X_Symbol sym, Reference ref) {
        if (frame.size() != 0) { frame.defReference(sym, ref); return; }
        Globals.put(sym, ref);
    }

    /**
     * 変数を値で定義する
     * @param sym 変数
     * @param obj 値
     */
    static void defValue(X_Symbol sym, X_Object obj) {
        if (frame.size() != 0) { frame.defValue(sym, obj); return; }
        Reference ref = register(obj);
        Globals.put(sym, ref);
    }

    static Reference register(X_Object obj) {
        entities.put(new X_Address(entities.lastKey().getAddress() + 1), obj);
        return new Reference(entities.lastKey().getAddress());
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
                System.out.println(entities);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
