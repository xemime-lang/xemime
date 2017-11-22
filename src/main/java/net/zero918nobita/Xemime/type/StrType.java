package net.zero918nobita.Xemime.type;

public class StrType implements Type {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof StrType;
    }

    @Override
    public String toString() {
        return "String";
    }
}
