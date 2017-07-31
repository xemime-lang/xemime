package net.zero918nobita.Xemime;

import java.util.HashMap;
import java.util.TreeMap;

class Interpreter {
    private static HashMap<String, Reference> consts;
    private static HashMap<String, Reference> local;
    private static HashMap<String, Reference> params;
    private static TreeMap<String, X_Object> entities;

    Interpreter() {
        consts = new HashMap<>();
        local = new HashMap<>();
        params = new HashMap<>();
        entities = new TreeMap<>();
        initializeBuiltinFuncs();
    }

    /**
     * 実体テーブルに、指定したオブジェクトを追加する
     * @param obj 実体テーブルに追加するオブジェクト
     * @return 追加されたオブジェクトを指す参照オブジェクト
     */
    private static Reference register(X_Object obj) {
        entities.put(String.valueOf(entities.size()), obj);
        return new Reference(Integer.parseInt(entities.lastKey()));
    }

    /**
     * 組み込み関数を読み込む
     */
    private static void initializeBuiltinFuncs() {
        consts.put("lambda", register(new X_Object()));
    }

    private static boolean isConst(X_Symbol symbol) {
        return (consts.containsKey(symbol.getName()));
    }

    private static boolean isGlobalVar(X_Symbol symbol) {
        return (params.containsKey(symbol.getName()));
    }

    private static boolean isLocalVar(X_Symbol symbol) {
        return (local.containsKey(symbol.getName()));
    }

    /**
     * シンボルの値を取得する
     * @param symbol シンボル
     * @return シンボルの値
     */
    X_Object getValueOfSymbol(X_Symbol symbol) {
        if (isLocalVar(symbol)) return local.get(symbol.getName()).fetch(entities);
        if (isConst(symbol)) return consts.get(symbol.getName()).fetch(entities);
        if (isGlobalVar(symbol)) return params.get(symbol.getName()).fetch(entities);
        return null;
    }

    /**
     * 変数に値をセットする
     * @param sym シンボル
     * @param obj 値
     */
    void set(X_Symbol sym, X_Object obj) {
        params.put(sym.getName(), register(obj));
    }
}
