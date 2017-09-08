package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * メソッド呼び出し部分を構文解析します。
 * @author Kodai Matsumoto
 */

class MethodCall extends ParseUnit {
    MethodCall(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        return null;
    }
}
