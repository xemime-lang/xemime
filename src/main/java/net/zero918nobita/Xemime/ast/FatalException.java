package net.zero918nobita.Xemime.ast;

class FatalException extends Exception {
    FatalException(int location, int errorCode) {
        super(location + ": インタプリタ内部の深刻なエラーが発生しました。 [" + errorCode + "]");
    }
}
