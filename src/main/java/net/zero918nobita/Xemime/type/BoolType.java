package net.zero918nobita.Xemime.type;

/**
 * 真偽型
 * @author Kodai Matsumoto
 */

public class BoolType implements Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }
}
