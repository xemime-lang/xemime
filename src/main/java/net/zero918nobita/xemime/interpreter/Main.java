package net.zero918nobita.xemime.interpreter;

import net.zero918nobita.xemime.entity.*;
import net.zero918nobita.xemime.parser.Parser;
import net.zero918nobita.xemime.ast.*;
import net.zero918nobita.xemime.resolver.Marshaller;
import net.zero918nobita.xemime.resolver.Ruminator;
import net.zero918nobita.xemime.type.AnyType;
import net.zero918nobita.xemime.type.Type;
import net.zero918nobita.xemime.type.UnitType;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static Parser parser;

    /** 実体テーブル */
    private static TreeMap<Address, Node> entities = new TreeMap<Address, Node>() {{
        put(new Address(0, 0), Bool.Nil);
        put(new Address(0, 1), Bool.T);
    }};

    /** グローバル環境 */
    public static Default defaultObj = new Default() {{
        Address addressOfDefaultObj = Main.register(defaultObj);
        setMember(Symbol.intern("this"), addressOfDefaultObj);
        setMember(Symbol.intern("THIS"), addressOfDefaultObj);
        setMember(Symbol.intern("Default"), addressOfDefaultObj);
        setMember(Symbol.intern("Core"), register(new X_Core()));
        setMember(Symbol.intern("Object"), register(new X_Object()));
    }};

    /**
     * ローカル変数のフレーム
     * ブロック式の評価が始まった直後に新たなフレームが追加され、
     * 評価が完了した直後に、最後に追加されたフレームが破棄されます。
     */
    public static Frame frame = new Frame();

    /** Core.exit(); の使用を認めるならば true が、そうでなければ false が代入されます。 */
    private static boolean allowExitMethod;

    /**
     * ローカル変数のフレームを追加します。
     * @param table フレーム
     */
    public static void loadLocalFrame(Handler table) {
        frame.loadLocalFrame(table);
    }

    /** 最後に追加したフレームを破棄します。 */
    public static void unloadLocalFrame() {
        frame.unloadLocalFrame();
    }

    public static boolean hasSymbol(Symbol sym) {
        return frame.hasSymbol(sym) || defaultObj.hasMember(sym);
    }

    public static Address getAddressOfSymbol(Symbol sym) throws Exception {
        return (frame.hasSymbol(sym)) ?
                frame.getAddressOfSymbol(sym) :
                defaultObj.getAddressOfMember(sym);
    }

    public static Node getValueOfSymbol(Symbol sym) throws Exception {
        if (frame.hasSymbol(sym)) {
            return frame.getValueOfSymbol(sym);
        } else {
            return (defaultObj.hasMember(sym)) ?
                    defaultObj.message(0, sym) : null;
        }
    }

    public static Node getValueOfReference(Address address) {
        return entities.get(address);
    }

    /** シンボルに参照をセットする */
    public static void setAddress(Symbol sym, Address ref) throws Exception {
        if (frame.hasSymbol(sym)) { frame.setAddress(sym, ref); return; }
        if (!defaultObj.hasMember(sym)) throw new Exception(parser.getLocation() + ": 変数 `" + sym.getName() + "` は宣言されていません");
        defaultObj.setMember(sym, ref);
    }

    /** 宣言済みの変数に値をセットする */
    public static void setValue(Symbol sym, Node obj) throws Exception {
        if (frame.hasSymbol(sym)) { frame.setValue(sym, obj); return; }
        Address ref = register(obj);
        // 変数が宣言されていない場合に例外を投げる
        if (!defaultObj.hasMember(sym)) throw new Exception(parser.getLocation() + ": 変数 `" + sym.getName() + "` は宣言されていません");
        defaultObj.setMember(sym, ref);
    }

    /** 変数を参照で宣言する */
    public static void defAddress(Symbol sym, Address ref) throws Exception {
        if (frame.numberOfLayers() != 0) { frame.defAddress(sym, ref); return; }
        defaultObj.setMember(sym, ref);
    }

    /** 変数を値で宣言する */
    public static void defValue(Symbol sym, Node obj) throws Exception {
        if (frame.numberOfLayers() != 0) { frame.defValue(sym, obj); return; }
        Address ref = register(obj);
        defaultObj.setMember(sym, ref);
    }

    /**
     * 指定した実体を実体テーブルに追加し、そのテーブル上での位置を記録した Address インスタンスを返します。
     * @param node 追加する実体
     * @return 実体テーブル上の、追加された実体の位置を記録した Address インスタンス
     */
    public static Address register(Node node) {
        entities.put(new Address(0,entities.lastKey().getAddress() + 1), node);
        return new Address(0, entities.lastKey().getAddress());
    }

    /**
     * xemime インタプリタにおいて最初に呼び出され、
     * コマンドライン引数によって実行モード(対話的実行 or ソースファイル実行)を設定し、
     * 実際にパーサも読み込んで解釈と実行を開始します。
     */
    public static void main(String[] args) {
        allowExitMethod = true;

        if (args.length >= 2) {
            usage();
            System.out.println(System.lineSeparator() + "Usage: java -jar xemime.jar [source file name]");
            return;
        }

        try {
            parser = new Parser();
            BufferedReader in;
            if (args.length == 0) {
                usage();
                in = new BufferedReader(new InputStreamReader(System.in));
                System.out.print(System.lineSeparator() + "[1]> ");
                String line;
                while (true) {
                    line = in.readLine();
                    if (line != null && !line.equals("")) {
                        ArrayList<Node> result;
                        try {
                            result = parser.parse(line);
                            result = Marshaller.marshal(result);
                            parser.getResolver().finishResolving();
                            Ruminator.ruminate(parser.getPostponedSymbols(), parser.getResolver());
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                            System.out.print("[" + (parser.getLocation() + 1) + "]> ");
                            parser.goDown(1);
                            continue;
                        }
                        for (Node c : result) {
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
                ArrayList<Node> result = null;
                try {
                    result = parser.parse(stringBuilder.toString());
                    result = Marshaller.marshal(result);
                    parser.getResolver().finishResolving();
                    Ruminator.ruminate(parser.getPostponedSymbols(), parser.getResolver());
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                for (Node c : result) {
                    try {
                        c.run();
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }
            }
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** 指定したソースコードの実行 */
    public static void exec(String source) throws Exception {
        allowExitMethod = false;
        parser = new Parser();
        ArrayList<Node> result = parser.parse(source);
        result = Marshaller.marshal(result);
        parser.getResolver().finishResolving();
        Ruminator.ruminate(parser.getPostponedSymbols(), parser.getResolver());
        for (Node node : result) node.run();
    }

    public static boolean allowExitMethod() {
        return allowExitMethod;
    }

    /** ロゴとバージョン情報の出力 */
    private static void usage() {
        System.out.println("   _  __               _              \n" +
                "  | |/ /__  ____ ___  (_)___ ___  ___ \n" +
                "  |   / _ \\/ __ `__ \\/ / __ `__ \\/ _ \\\n" +
                " /   /  __/ / / / / / / / / / / /  __/\n" +
                "/_/|_\\___/_/ /_/ /_/_/_/ /_/ /_/\\___/ \n\n" +
                "xemime Version 1.0.0 2017-11-23");
    }

    /**
     * Object 組み込みオブジェクト
     * オブジェクト生成、クローン処理を担うメソッドを実装した最も基本的なオブジェクトです。<br>
     * これをクローンして新たなオブジェクトを用意することを推奨しています。
     */
    private static class X_Object extends Handler {
        X_Object() {
            super(0);
            setMember(Symbol.intern("clone"), new X_Clone());
            setMember(Symbol.intern("new"), new X_New());
            setMember(Symbol.intern("proto"), new Bool(0, false));
        }

        /**
         * Object.clone メソッド
         * clone メソッドのみを実装した、最も単純なオブジェクトを生成してその参照を返します。
         */
        private static class X_Clone extends Native {
            X_Clone() {
                super(0, new LinkedHashMap<>(), new AnyType());
            }

            @Override
            protected Address exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
                return Main.register(params.get(Symbol.intern("this")).run());
            }

            @Override
            protected Node exec(ArrayList<Node> params, Address self) throws Exception {
                return exec(new LinkedHashMap<Symbol, Node>(){{
                    put(Symbol.intern("this"), params.get(0));
                }}, self);
            }
        }

        private static class X_New extends Native {
            X_New() {
                super(0, new LinkedHashMap<>(), new AnyType());
            }

            @Override
            protected Node exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
                Handler obj1 = (Handler) params.get(Symbol.intern("this")).run();
                Handler obj2 = new Handler(0);
                obj2.setMember(Symbol.intern("proto"), new Bool(0, false));
                if (obj1.hasMember(Symbol.intern("proto"))) {
                    Handler proto = (Handler) obj1.getMember(Symbol.intern("proto"));
                    for (Map.Entry<Symbol, Address> entry : proto.getMembers().entrySet()) {
                        obj2.setMember(entry.getKey(), entry.getValue());
                    }
                }
                return Main.register(obj2);
            }

            @Override
            protected Node exec(ArrayList<Node> params, Address self) throws Exception {
                return exec(new LinkedHashMap<Symbol, Node>(){{
                    put(Symbol.intern("this"), params.get(0));
                }}, self);
            }
        }
    }

    /**
     * Core 組み込みオブジェクト<br>
     * 条件分岐や、繰り返し、入出力、インタプリタの終了処理などを担うメソッドを実装した組み込みオブジェクトです。
     */
    private static class X_Core extends Handler {
        X_Core() {
            super(0);
            setMember(Symbol.intern("print"), new X_Print());
            setMember(Symbol.intern("println"), new X_Println());
            setMember(Symbol.intern("exit"), new X_Exit());
        }

        /**
         * Core.exit メソッド<br>
         * xemime インタプリタを終了します。
         */
        private static class X_Exit extends Native {
            X_Exit() {
                super(0, new LinkedHashMap<>(), UnitType.gen());
            }

            @Override
            protected Node exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
                if (Main.allowExitMethod) System.exit(0);
                throw new Exception("この実行環境で Core.exit メソッドを使用することはできません");
            }

            @Override
            protected Node exec(ArrayList<Node> params, Address self) throws Exception {
                return exec(new LinkedHashMap<Symbol, Node>(){{
                    put(Symbol.intern("this"), params.get(0));
                }}, self);
            }
        }

        /**
         * Core.print メソッド<br>
         * 1つの引数を受け取り、それを文字列化して標準出力に出力します。
         */
        private static class X_Print extends Native {
            X_Print() {
                super(0, new LinkedHashMap<Symbol, Type>(){{
                    put(Symbol.intern("target"), new AnyType());
                }}, new UnitType());
            }

            @Override
            protected Node exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
                System.out.print(params.get(Symbol.intern("target")).run());
                return new Unit(getLocation(), null);
            }

            @Override
            protected Node exec(ArrayList<Node> params, Address self) throws Exception {
                return exec(new LinkedHashMap<Symbol, Node>(){{
                    put(Symbol.intern("this"), params.get(0));
                    put(Symbol.intern("target"), params.get(1));
                }}, self);
            }
        }

        /**
         * Core.println メソッド<br>
         * 1つの引数を受け取り、それを文字列化し末尾に改行コードを付与して標準出力に出力します。
         */
        private static class X_Println extends Native {
            X_Println() {
                super(0, new LinkedHashMap<Symbol, Type>(){{
                    put(Symbol.intern("target"), new AnyType());
                }}, new UnitType());
            }

            @Override
            protected Node exec(LinkedHashMap<Symbol, Node> params, Address self) throws Exception {
                System.out.println(params.get(Symbol.intern("target")).run());
                return new Unit(getLocation(), null);
            }

            @Override
            protected Node exec(ArrayList<Node> params, Address self) throws Exception {
                return exec(new LinkedHashMap<Symbol, Node>(){{
                    put(Symbol.intern("this"), params.get(0));
                    put(Symbol.intern("target"), params.get(1));
                }}, self);
            }
        }
    }
}
