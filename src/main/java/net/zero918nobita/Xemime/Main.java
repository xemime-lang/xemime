package net.zero918nobita.Xemime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * エントリーポイント
 * @author Kodai Matsumoto
 */

public class Main {
    private static VirtualMemoryMonitor vmm = null;
    private static Thread vmmThread = null;
    private static Parser parser;
    static X_Default defaultObj = new X_Default();
    private static TreeMap<X_Address, X_Code> entities = new TreeMap<X_Address, X_Code>() {{
        put(new X_Address(0,0), X_Bool.T);
    }};
    static Frame frame = new Frame();

    static void loadLocalFrame(X_Handler table) {
        frame.loadLocalFrame(table);
    }

    static void unloadLocalFrame() {
        frame.unloadLocalFrame();
    }

    static boolean hasSymbol(X_Symbol sym) {
        return frame.hasSymbol(sym) || defaultObj.hasMember(sym);
    }

    /**
     * シンボルの参照先アドレスを取得する
     * @param sym シンボル
     * @return 参照
     */
    static X_Address getAddressOfSymbol(X_Symbol sym) throws Exception {
        return (frame.hasSymbol(sym)) ?
                frame.getAddressOfSymbol(sym) :
                (X_Address) defaultObj.getMember(sym);
    }

    /**
     * シンボルの値を取得する
     * @param sym シンボル
     * @return 値
     */
    static X_Code getValueOfSymbol(X_Symbol sym) throws Exception {
        if (frame.hasSymbol(sym)) {
            return frame.getValueOfSymbol(sym);
        } else {
            return (defaultObj.hasMember(sym)) ?
                    defaultObj.message(0, sym) : null;
        }
    }

    /**
     * 参照先の実体を取得する
     * @param address アドレス
     * @return 実体
     */
    static X_Code getValueOfReference(X_Address address) {
        return entities.get(address);
    }

    /**
     * シンボルに参照をセットする
     * @param sym シンボル
     * @param ref 参照
     */
    static void setAddress(X_Symbol sym, X_Address ref) throws Exception {
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
    static void setValue(X_Symbol sym, X_Code obj) throws Exception {
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
    static void defAddress(X_Symbol sym, X_Address ref) throws Exception {
        if (frame.numberOfLayers() != 0) { frame.defAddress(sym, ref); return; }
        defaultObj.setMember(sym, ref);
    }

    /**
     * 変数を値で宣言します。
     * @param sym 変数
     * @param obj 値
     */
    static void defValue(X_Symbol sym, X_Code obj) throws Exception {
        if (frame.numberOfLayers() != 0) { frame.defValue(sym, obj); return; }
        X_Address ref = register(obj);
        defaultObj.setMember(sym, ref);
    }

    static X_Address register(X_Code obj) {
        entities.put(new X_Address(0,entities.lastKey().getAddress() + 1), obj);
        return new X_Address(0, entities.lastKey().getAddress());
    }

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

        defaultObj.setMember(X_Symbol.intern(0, "this"), defaultObj);
        defaultObj.setMember(X_Symbol.intern(0, "THIS"), defaultObj);
        defaultObj.setMember(X_Symbol.intern(0, "Default"), defaultObj);
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
                        ArrayList<X_Code> result = parser.parse(line);
                        for (X_Code c : result) System.out.println(c.run());
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
                ArrayList<X_Code> result = parser.parse(stringBuilder.toString());
                for (X_Code c : result) c.run();
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
                "Xemime Version 1.0.0 2017-08-07");
    }

    private static class X_Object extends X_Handler {
        X_Object() {
            super(0);
            setMember(X_Symbol.intern(0, "clone"), new X_Clone());
        }

        private static class X_Clone extends X_Native {
            X_Clone() {
                super(0, 0);
            }

            @Override
            protected X_Address exec(ArrayList<X_Code> params, X_Address self) throws Exception {
                return Main.register(params.get(0).run());
            }
        }
    }

    private static class X_Core extends X_Handler {
        X_Core() {
            super(0);
            setMember(X_Symbol.intern(0, "if"), new X_If());
            setMember(X_Symbol.intern(0, "print"), new X_Print());
            setMember(X_Symbol.intern(0, "println"), new X_Println());
            setMember(X_Symbol.intern(0, "exit"), new X_Exit());
        }

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
