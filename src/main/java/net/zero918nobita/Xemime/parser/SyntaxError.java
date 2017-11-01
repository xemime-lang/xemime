package net.zero918nobita.Xemime.parser;

/**
 * 文法エラー
 * @author Kodai Matsumoto
 */

class SyntaxError extends Exception {
    SyntaxError(int location, int errorCode, String message) {
        super(location + ": 文法エラーです ( " + message + " ) [" + errorCode + "]");
    }
}
