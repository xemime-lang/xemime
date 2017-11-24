package net.zero918nobita.Xemime.type;

public class Type {
    private static Type myself;
    public boolean needsRuminating = false;

    public static Type gen() {
        if (myself == null) myself = new Type();
        return myself;
    }
}
