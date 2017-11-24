package net.zero918nobita.Xemime.type;

/**
 * 真偽型
 * @author Kodai Matsumoto
 */

public class BoolType extends Type {
    private static BoolType myself;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "Bool";
    }

    public static BoolType gen() {
        if (myself == null) myself = new BoolType();
        return myself;
    }
}
