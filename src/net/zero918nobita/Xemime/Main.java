package net.zero918nobita.Xemime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * エントリーポイント
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
        if (frame.hasSymbol(sym)) {
            return frame.getValueOfSymbol(sym, entities);
        } else {
            return (globalSymbols.containsKey(sym)) ?
                    globalSymbols.get(sym).fetch(entities) : null;
        }
    }

    /**
     * シンボルに参照をセットする
     * @param sym シンボル
     * @param ref 参照
     */
    static void setAddress(X_Symbol sym, X_Address ref) throws Exception {
        if (frame.hasSymbol(sym)) { frame.setAddress(sym, ref); return; }
        if (!globalSymbols.containsKey(sym)) throw new Exception("変数 `" + sym.getName() + "` は宣言されていません");
        globalSymbols.put(sym, ref);
    }

    /**
     * 宣言済みの変数に値をセットします。
     * @param sym シンボル
     * @param obj 値
     * @throws Exception 変数が宣言されていなかった場合に例外を発生させます。
     */
    static void setValue(X_Symbol sym, X_Object obj) throws Exception {
        if (frame.hasSymbol(sym)) { frame.setValue(sym, obj); return; }
        X_Address ref = register(obj);
        if (!globalSymbols.containsKey(sym)) throw new Exception("変数 `" + sym.getName() + "` は宣言されていません");
        globalSymbols.put(sym, ref);
    }

    /**
     * 変数を参照で宣言します。
     * @param sym 変数
     * @param ref 参照
     */
    static void defAddress(X_Symbol sym, X_Address ref) {
        if (frame.numberOfLayers() != 0) { frame.defAddress(sym, ref); return; }
        globalSymbols.put(sym, ref);
    }

    /**
     * 変数を値で宣言します。
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
        boolean interactive = false;

        if (args.length >= 2) {
            usage();
            return;
        }

        entities.put(new X_Address(0), X_Bool.T);
        globalSymbols.put(new X_Symbol("Core"), register(new X_Core()));

        try {
            Lexer lex;
            Parser parser = new Parser();
            BufferedReader in;
            if (args.length == 0) {
                in = new BufferedReader(new InputStreamReader(System.in));
                interactive = true;
            } else {
                in = new BufferedReader(new FileReader(args[0]));
            }
            if (interactive) System.out.print("> ");
            String line;
            while (true) {
                line = in.readLine();
                if (line != null && !line.equals("")) {
                    lex = new Lexer((interactive) ? line : line + " \n");
                    X_Object obj = parser.parse(lex);
                    if (obj == null) break;
                    if (interactive) {
                        System.out.println(obj.run().toString());
                        System.out.print("> ");
                    } else {
                        obj.run();
                    }
                } else if (line == null) {
                    break;
                }
            }
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void usage() {
        System.out.println("   _  __               _              \n" +
                "  | |/ /__  ____ ___  (_)___ ___  ___ \n" +
                "  |   / _ \\/ __ `__ \\/ / __ `__ \\/ _ \\\n" +
                " /   /  __/ / / / / / / / / / / /  __/\n" +
                "/_/|_\\___/_/ /_/ /_/_/_/ /_/ /_/\\___/ \n\n" +
                "Xemime Version 1.0.0 2017-08-07\n\n" +
                "Usage: java -jar Xemime.jar [source file name]");
    }

    static class X_Core extends X_Object {
        X_Core() {
            super();
            setMember(new X_Symbol("if"), new X_If());
            setMember(new X_Symbol("println"), new X_Println());
        }

        private static class X_Println extends X_Native {
            X_Println() {
                super(2);
            }

            @Override
            protected X_Object exec(ArrayList<X_Object> params) throws Exception {
                X_Object o = params.get(1).run();
                System.out.println(o);
                return o;
            }
        }

        private static class X_If extends X_Native {
            X_If(){
                super(4);
            }

            @Override
            protected X_Object exec(ArrayList<X_Object> params) throws Exception {
                return (params.get(1).run().equals(X_Bool.Nil)) ? params.get(3).run() : params.get(2).run();
            }
        }
    }
}
