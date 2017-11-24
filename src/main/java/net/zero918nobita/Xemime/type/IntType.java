package net.zero918nobita.Xemime.type;

/**
 * 整数型
 * @author Kodai Matsumoto
 */

public class IntType extends Type {
    private static IntType myself;
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "Int";
    }

    public static IntType gen() {
        if (myself == null) myself = new IntType();
        return myself;
    }
}
