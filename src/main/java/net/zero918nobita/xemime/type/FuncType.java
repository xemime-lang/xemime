package net.zero918nobita.xemime.type;

import net.zero918nobita.xemime.ast.Symbol;

import java.util.LinkedHashMap;

/**
 * 関数の型情報
 * @author Kodai Matsumoto
 */

public class FuncType extends Type {
    private Type returnType;
    private LinkedHashMap<Symbol, Type> params;

    public FuncType(Type returnType, LinkedHashMap<Symbol, Type> params) {
        this.returnType = returnType;
        this.params = params;
    }

    public Type getReturnType() {
        return returnType;
    }
}
