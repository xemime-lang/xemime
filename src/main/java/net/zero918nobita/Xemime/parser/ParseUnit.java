package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

abstract class ParseUnit {
    protected Lexer lexer;
    protected TokenType tokenType;
    protected Resolver resolver;

    ParseUnit(Lexer lexer, Resolver resolver) {
        this.lexer = lexer;
        this.resolver = resolver;
    }

    void getToken() throws Exception {
        if (lexer.advance()) {
            tokenType = lexer.tokenType();
        } else {
            tokenType = TokenType.EOS;
        }
    }

    abstract Node parse() throws Exception;
}
