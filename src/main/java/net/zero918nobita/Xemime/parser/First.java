package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;

/**
 * 一次子を構文解析します。
 * @author Kodai Matsumoto
 */

class First extends ParseUnit {
    First(Lexer lexer) {
        super(lexer);
    }

    @Override
    Node parse() throws Exception {
        return null;
    }
}
