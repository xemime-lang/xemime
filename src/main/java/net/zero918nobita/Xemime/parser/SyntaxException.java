package net.zero918nobita.Xemime.parser;

class SyntaxException extends Exception {
    SyntaxException(int location, int errorCode, String message) {
        super(location + ": 文法エラーです ( " + message + " ) [" + errorCode + "]");
    }
}
