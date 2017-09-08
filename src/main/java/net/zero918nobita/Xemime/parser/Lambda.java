package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;

/**
 * ラムダ式を構文解析します。
 * @author Kodai Matsumoto
 */

class Lambda extends ParseUnit {
    Lambda(Lexer lexer) {
        super(lexer);
    }

    @Override
    Node parse() throws Exception {
        return null;
    }
}
