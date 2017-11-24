package net.zero918nobita.Xemime.type;

public class StrType extends Type {
    private static StrType myself;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StrType;
    }

    @Override
    public String toString() {
        return "String";
    }

    public static StrType gen() {
        if (myself == null) myself = new StrType();
        return myself;
    }
}
