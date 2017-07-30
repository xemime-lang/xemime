package net.zero918nobita.Xemime;

import java.util.HashMap;

/**
 * 環境 ( 変数テーブル、行番号 )
 * @author Kodai Matsumoto
 */

class Environment {
    private int location;
    private Interpreter interpreter;
    private HashMap<String, Reference> local;

    Environment(int line, Interpreter interpreter, HashMap<String, Reference> local) {
        this.location = line;
        this.interpreter = interpreter;
        this.local = local;
    }

    Interpreter getInterpreter() {
        return interpreter;
    }

    HashMap<String, Reference> getLocal() {
        return local;
    }
}
