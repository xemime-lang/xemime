package net.zero918nobita.xemime.resolver;

public class TypeError extends Exception {
    public TypeError(int location, int errorCode, String message) {
        super(location + ": " + message + " [" + errorCode + "]");
    }
}
