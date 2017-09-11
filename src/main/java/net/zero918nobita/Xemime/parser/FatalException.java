package net.zero918nobita.Xemime.parser;

class FatalException extends Exception {
    FatalException(int location, int errorCode) {
        super(location + ": インタプリタ内部の深刻な不具合が発生しました [" + errorCode + "]");
    }
}
