package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;

abstract class ParseUnit {
    protected Lexer lexer;
    protected TokenType tokenType;

    ParseUnit(Lexer lexer) {
        this.lexer = lexer;
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
