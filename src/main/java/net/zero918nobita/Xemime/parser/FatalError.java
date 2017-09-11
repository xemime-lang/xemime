package net.zero918nobita.Xemime.parser;

class FatalError extends Exception {
    FatalError(int location, int errorCode) {
        super(location + ": インタプリタ内部の深刻な不具合が発生しました [" + errorCode + "]");
    }
}
