package net.zero918nobita.Xemime.type;

/**
 * 配列型
 * @author Kodai Matsumoto
 */

public class ArrayType extends Type {
    private Type type;
    public ArrayType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[" + type + "]";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ArrayType && ((ArrayType) object).type.equals(type);
    }

    public Type getType() {
        return type;
    }
}
