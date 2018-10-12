package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/** 各文法要素を構文解析するクラスの原型 */
public abstract class ParseUnit {
    protected Lexer lexer;
    protected Resolver resolver;
    private boolean retreat = false;

    public ParseUnit(Lexer lexer, Resolver resolver) {
        this.lexer = lexer;
        this.resolver = resolver;
    }

    public void getToken() throws Exception {
        if (retreat) {
            retreat = false;
            return;
        }
        if (lexer.advance()) {
            lexer.setTokenType(lexer.tokenType());
        } else {
            lexer.setTokenType(TokenType.EOS);
        }
    }

    void retreat() {
        retreat = true;
    }

    void skipLineBreaks() throws Exception {
        while (lexer.tokenType() == TokenType.BR) getToken();
    }

    boolean current(TokenType tokenType) {
        return lexer.tokenType() == tokenType;
    }

    protected abstract Node parse() throws Exception;
}
