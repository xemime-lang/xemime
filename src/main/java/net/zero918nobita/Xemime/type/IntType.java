package net.zero918nobita.Xemime.type;

/**
 * 整数型
 * @author Kodai Matsumoto
 */

public class IntType implements Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "Int";
    }
}
