package net.zero918nobita.Xemime.type;

public class AnyType implements Type {
    @Override
    public String toString() {
        return "Any";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnyType;
    }
}
