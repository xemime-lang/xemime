package net.zero918nobita.Xemime.parser;

class SyntaxError extends Exception {
    SyntaxError(int location, int errorCode, String message) {
        super(location + ": 文法エラーです ( " + message + " ) [" + errorCode + "]");
    }
}
