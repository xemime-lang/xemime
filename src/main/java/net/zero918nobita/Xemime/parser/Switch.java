package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * switch 文の構文解析を行います。
 * @author Kodai Matsumoto
 */

class Switch extends ParseUnit {
    Switch(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        return null;
    }
}
