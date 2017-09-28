package net.zero918nobita.Xemime.resolver;

public class TypeError extends Exception {
    public TypeError(int location, int errorCode, String messsage) {
        super(location + ": " + messsage + " [" + errorCode + "]");
    }
}
