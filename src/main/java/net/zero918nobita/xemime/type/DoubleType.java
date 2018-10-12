package net.zero918nobita.xemime.type;

/**
 * 小数型
 * @author Kodai Matsumoto
 */

public class DoubleType extends Type {
    private static DoubleType myself;
    @Override
    public boolean equals(Object obj) {
        return obj instanceof DoubleType;
    }

    @Override
    public String toString() {
        return "Double";
    }

    public static DoubleType gen() {
        if (myself == null) myself = new DoubleType();
        return myself;
    }
}
