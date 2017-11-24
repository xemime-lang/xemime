package net.zero918nobita.Xemime.type;

public class AnyType extends Type {
    private static AnyType myself;

    @Override
    public String toString() {
        return "Any";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnyType;
    }

    public static AnyType gen() {
        if (myself == null) myself = new AnyType();
        return myself;
    }
}
