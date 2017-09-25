package net.zero918nobita.Xemime.parser;

public class SyntaxError extends Exception {
    public SyntaxError(int location, int errorCode, String message) {
        super(location + ": 文法エラーです ( " + message + " ) [" + errorCode + "]");
    }
}
