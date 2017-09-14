package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 各文法要素を構文解析するクラスの原型です。
 * @author Kodai Matsumoto
 */

abstract class ParseUnit {
    protected Lexer lexer;
    protected Resolver resolver;

    ParseUnit(Lexer lexer, Resolver resolver) {
        this.lexer = lexer;
        this.resolver = resolver;
    }

    void getToken() throws Exception {
        if (lexer.advance()) {
            lexer.setTokenType(lexer.tokenType());
        } else {
            lexer.setTokenType(TokenType.EOS);
        }
    }

    abstract Node parse() throws Exception;
}
