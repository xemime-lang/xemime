package net.zero918nobita.Xemime.type;

import net.zero918nobita.Xemime.ast.Symbol;

import java.util.TreeMap;

/**
 * 関数の型情報
 * @author Kodai Matsumoto
 */

public class FuncType implements Type {
    private Type returnType;
    private TreeMap<Symbol, Type> params;

    public FuncType(Type returnType, TreeMap<Symbol, Type> params) {
        this.returnType = returnType;
        this.params = params;
    }
}
