package net.zero918nobita.Xemime.interpreter;

import net.zero918nobita.Xemime.parser.Parser;
import net.zero918nobita.Xemime.utils.VirtualMemoryMonitor;
import net.zero918nobita.Xemime.ast.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * エントリーポイント
 * @author Kodai Matsumoto
 */

public class Main {

    /** 構文解析器 */
    private static Parser parser;

    /** グローバル環境 */
    public static X_Default defaultObj = new X_Default();

    /** 実体テーブル */
    private static TreeMap<X_Address, X_Code> entities = new TreeMap<X_Address, X_Code>() {{
        put(new X_Address(0, 0), X_Bool.Nil);
        put(new X_Address(0, 1), X_Bool.T);
    }};

    /**
     * ローカル変数のフレーム<br>
     * ブロック式の評価が始まった直後に新たなフレームが追加され、
     * 評価が完了した直後に、最後に追加されたフレームが破棄されます。
     */
    public static Frame frame = new Frame();

    /** 仮想メモリモニタ */
    private static VirtualMemoryMonitor vmm = null;

    /** 仮想メモリモニタ実行用スレッド */
    private static Thread vmmThread = null;

    /**
     * ローカル変数のフレームを追加します。
     * @param table フレーム
     */
    public static void loadLocalFrame(X_Handler table) {
        frame.loadLocalFrame(table);
    }

    /** 最後に追加したフレームを破棄します。 */
    public static void unloadLocalFrame() {
        frame.unloadLocalFrame();
    }

    /**
     * 指定したシンボルがすでに変数テーブルに記録されているかを調べます。
     * @param sym シンボル
     * @return 記録されていれば true 、そうでなければ false
     */
    public static boolean hasSymbol(X_Symbol sym) {
        return frame.hasSymbol(sym) || defaultObj.hasMember(sym);
    }

    /**
     * シンボルの参照先アドレスを取得します。
     * @param sym シンボル
     * @return 参照
     */
    public static X_Address getAddressOfSymbol(X_Symbol sym) throws Exception {
        return (frame.hasSymbol(sym)) ?
                frame.getAddressOfSymbol(sym) :
                defaultObj.getAddressOfMember(sym);
    }

    /**
     * シンボルの値を取得します。
     * @param sym シンボル
     * @return 値
     */
    public static X_Code getValueOfSymbol(X_Symbol sym) throws Exception {
        if (frame.hasSymbol(sym)) {
            return frame.getValueOfSymbol(sym);
        } else {
            return (defaultObj.hasMember(sym)) ?
                    defaultObj.message(0, sym) : null;
        }
    }

    /**
     * 参照先の実体を取得します。
     * @param address アドレス
     * @return 実体
     */
    public static X_Code getValueOfReference(X_Address address) {
        return entities.get(address);
    }

    /**
     * シンボルに参照をセットします。
     * @param sym シンボル
     * @param ref 参照
     */
    public static void setAddress(X_Symbol sym, X_Address ref) throws Exception {
        if (frame.hasSymbol(sym)) { frame.setAddress(sym, ref); return; }
        if (!defaultObj.hasMember(sym)) throw new Exception(parser.getLocation() + ": 変数 `" + sym.getName() + "` は宣言されていません");
        defaultObj.setMember(sym, ref);
    }

    /**
     * 宣言済みの変数に値をセットします。
     * @param sym シンボル
     * @param obj 値
     * @throws Exception 変数が宣言されていなかった場合に例外を発生させます。
     */
    public static void setValue(X_Symbol sym, X_Code obj) throws Exception {
        if (frame.hasSymbol(sym)) { frame.setValue(sym, obj); return; }
        X_Address ref = register(obj);
        if (!defaultObj.hasMember(sym)) throw new Exception(parser.getLocation() + ": 変数 `" + sym.getName() + "` は宣言されていません");
        defaultObj.setMember(sym, ref);
    }

    /**
     * 変数を参照で宣言します。
     * @param sym 変数
     * @param ref 参照
     */
    public static void defAddress(X_Symbol sym, X_Address ref) throws Exception {
        if (frame.numberOfLayers() != 0) { frame.defAddress(sym, ref); return; }
        defaultObj.setMember(sym, ref);
    }

    /**
     * 変数を値で宣言します。
     * @param sym 変数
     * @param obj 値
     */
    public static void defValue(X_Symbol sym, X_Code obj) throws Exception {
        if (frame.numberOfLayers() != 0) { frame.defValue(sym, obj); return; }
        X_Address ref = register(obj);
        defaultObj.setMember(sym, ref);
    }

    /**
     * 指定した実体を実体テーブルに追加し、そのテーブル上での位置を記録した X_Address インスタンスを返します。
     * @param obj 追加する実体
     * @return 実体テーブル上の、追加された実体の位置を記録した X_Address インスタンス
     */
    public static X_Address register(X_Code obj) {
        entities.put(new X_Address(0,entities.lastKey().getAddress() + 1), obj);
        return new X_Address(0, entities.lastKey().getAddress());
    }

    /**
     * Xemime インタプリタにおいて最初に呼び出され、
     * コマンドライン引数によって実行モード(対話的実行 or ソースファイル実行)を設定し、
     * 実際にパーサも読み込んで解釈と実行を開始します。<br>
     * -debug オプションが設定されていた場合、仮想メモリモニタを別スレッドで起動します。
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        boolean debug = Arrays.asList(args).contains("-debug");

        if ((debug && args.length >= 3) || (!debug && args.length >= 2)) {
            usage();
            System.out.println(System.lineSeparator() + "Usage: java -jar Xemime.jar [source file name]");
            return;
        }

        if (debug) {
            vmm = new VirtualMemoryMonitor();
            vmmThread = new Thread(vmm);
            vmmThread.start();
        }

        X_Address addressOfDefaultObj = Main.register(defaultObj);
        defaultObj.setMember(X_Symbol.intern(0, "this"), addressOfDefaultObj);
        defaultObj.setMember(X_Symbol.intern(0, "THIS"), addressOfDefaultObj);
        defaultObj.setMember(X_Symbol.intern(0, "Default"), addressOfDefaultObj);
        defaultObj.setMember(X_Symbol.intern(0, "Core"), register(new X_Core()));
        defaultObj.setMember(X_Symbol.intern(0, "Object"), register(new X_Object()));

        try {
            parser = new Parser();
            BufferedReader in;
            if ((debug && args.length == 1) || (!debug && args.length == 0)) {
                usage();
                in = new BufferedReader(new InputStreamReader(System.in));
                System.out.print(System.lineSeparator() + "[1]> ");
                String line;
                while (true) {
                    line = in.readLine();
                    if (line != null && !line.equals("")) {
                        ArrayList<X_Code> result;
                        try {
                            result = parser.parse(line);
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                            System.out.print("[" + (parser.getLocation() + 1) + "]> ");
                            parser.goDown(1);
                            continue;
                        }
                        for (X_Code c : result) {
                            try {
                                System.out.println(c.run());
                            } catch(Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        }
                        System.out.print("[" + (parser.getLocation() + 1) + "]> ");
                        parser.goDown(1);
                    } else if (line == null) {
                        break;
                    }
                }
            } else {
                in = new BufferedReader(new FileReader(args[0]));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');
                }
                ArrayList<X_Code> result = null;
                try {
                    result = parser.parse(stringBuilder.toString());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                for (X_Code c : result) {
                    try {
                        c.run();
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }
            }
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ロゴとバージョン情報を出力します。
     */
    private static void usage() {
        System.out.println("   _  __               _              \n" +
                "  | |/ /__  ____ ___  (_)___ ___  ___ \n" +
                "  |   / _ \\/ __ `__ \\/ / __ `__ \\/ _ \\\n" +
                " /   /  __/ / / / / / / / / / / /  __/\n" +
                "/_/|_\\___/_/ /_/ /_/_/_/ /_/ /_/\\___/ \n\n" +
                "Xemime Version 1.0.0 2017-09-05");
    }

    /**
     * Object 組み込みオブジェクト<br>
     * オブジェクト生成、クローン処理を担うメソッドを実装した最も基本的なオブジェクトです。<br>
     * これをクローンして新たなオブジェクトを用意することを推奨しています。
     */
    private static class X_Object extends X_Handler {
        X_Object() {
            super(0);
            setMember(X_Symbol.intern(0, "clone"), new X_Clone());
            setMember(X_Symbol.intern(0, "new"), new X_New());
            setMember(X_Symbol.intern(0, "proto"), new X_Bool(0, false));
        }

        /**
         * Object.clone メソッド<br>
         * clone メソッドのみを実装した、最も単純なオブジェクトを生成してその参照を返します。
         */
        private static class X_Clone extends X_Native {
            X_Clone() {
                super(0, 0);
            }

            @Override
            protected X_Address exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                return Main.register(params.get(0).run());
            }
        }

        private static class X_New extends X_Native {
            X_New() {
                super(0, 0);
            }

            @Override
            protected X_Code exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                X_Handler obj1 = (X_Handler) params.get(0).run();
                X_Handler obj2 = new X_Handler(0);
                obj2.setMember(X_Symbol.intern(0, "proto"), new X_Bool(0, false));
                if (obj1.hasMember(X_Symbol.intern(0, "proto"))) {
                    X_Handler proto = (X_Handler) obj1.getMember(X_Symbol.intern(0, "proto"));
                    for (Map.Entry<X_Symbol, X_Address> entry : proto.getMembers().entrySet()) {
                        obj2.setMember(entry.getKey(), entry.getValue());
                    }
                }
                return Main.register(obj2);
            }
        }
    }

    /**
     * Core 組み込みオブジェクト<br>
     * 条件分岐や、繰り返し、入出力、インタプリタの終了処理などを担うメソッドを実装した組み込みオブジェクトです。
     */
    private static class X_Core extends X_Handler {
        X_Core() {
            super(0);
            setMember(X_Symbol.intern(0, "if"), new X_If());
            setMember(X_Symbol.intern(0, "print"), new X_Print());
            setMember(X_Symbol.intern(0, "println"), new X_Println());
            setMember(X_Symbol.intern(0, "exit"), new X_Exit());
        }

        /**
         * Core.exit メソッド<br>
         * Xemime インタプリタを終了します。
         */
        private static class X_Exit extends X_Native {
            X_Exit() {
                super(0, 0);
            }

            @Override
            protected X_Code exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                System.exit(0);
                return new X_Int(0, 0);
            }
        }

        /**
         * Core.print メソッド<br>
         * 1つの引数を受け取り、それを文字列化して標準出力に出力します。
         */
        private static class X_Print extends X_Native {
            X_Print() {
                super(0, 1);
            }

            @Override
            protected X_Code exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                X_Code o = params.get(1).run();
                System.out.print(o);
                return o;
            }
        }

        /**
         * Core.println メソッド<br>
         * 1つの引数を受け取り、それを文字列化し末尾に改行コードを付与して標準出力に出力します。
         */
        private static class X_Println extends X_Native {
            X_Println() {
                super(0, 1);
            }

            @Override
            protected X_Code exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                X_Code o = params.get(1).run();
                System.out.println(o);
                return o;
            }
        }

        /**
         * Core.if メソッド<br>
         * 条件式と2つの引数を受け取り、条件式を評価してNIL以外となった場合は2つ目の引数を、
         * NILとなった場合は3つ目の引数を評価して返します。
         */
        private static class X_If extends X_Native {
            X_If(){
                super(0, 3);
            }

            @Override
            protected X_Code exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                return (params.get(1).run().equals(X_Bool.Nil)) ? params.get(3).run() : params.get(2).run();
            }
        }
    }
}
