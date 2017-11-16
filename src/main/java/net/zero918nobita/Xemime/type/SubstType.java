package net.zero918nobita.Xemime.type;

import net.zero918nobita.Xemime.ast.Symbol;

/**
 * 実体型
 * @author Kodai Matsumoto
 */

public class SubstType implements Type {
    private Symbol attr_name;

    public SubstType(Symbol attr_name) {
        this.attr_name = attr_name;
    }
}
