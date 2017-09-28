package net.zero918nobita.Xemime.type;

/**
 * 小数型
 * @author Kodai Matsumoto
 */

public class DoubleType implements Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof DoubleType;
    }
}
