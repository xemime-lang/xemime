package net.zero918nobita.xemime.type;

import net.zero918nobita.xemime.ast.Symbol;

/**
 * 実体型
 * @author Kodai Matsumoto
 */

public class SubstType extends Type {
    private Symbol attr_name;

    public SubstType(Symbol attr_name) {
        this.attr_name = attr_name;
    }
}
