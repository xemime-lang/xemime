package net.zero918nobita.Xemime.type;

public class AnyType implements Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnyType;
    }
}
