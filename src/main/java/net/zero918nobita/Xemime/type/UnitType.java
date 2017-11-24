package net.zero918nobita.Xemime.type;

/**
 * unit 型
 * @author Kodai Matsumoto
 */

public class UnitType extends Type {
    private static UnitType myself;

    @Override
    public String toString() {
        return "Unit";
    }

    public static Type gen() {
        if (myself == null) myself = new UnitType();
        return myself;
    }
}
