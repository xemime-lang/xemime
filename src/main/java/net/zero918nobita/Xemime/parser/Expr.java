package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 式の構文解析を行います。
 * @author Kodai Matsumoto
 */

class Expr extends ParseUnit{
    Expr(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        Node node;

        while (lexer.tokenType() == TokenType.BR) getToken();

        switch (lexer.tokenType()) {
            case IF:
                node = new If(lexer, resolver).parse();
                break;
            case SWITCH:
                node = new Switch(lexer, resolver).parse();
                break;
            case FOR:
                node = new For(lexer, resolver).parse();
                break;
            case WHILE:
                node = new While(lexer, resolver).parse();
                break;
            default:
                node = new LogicalExpr(lexer, resolver).parse();
                if (lexer.tokenType() == TokenType.SEMICOLON) {
                    getToken();
                    node = new Unit(lexer.getLocation(), node);
                }
        }

        return node;
    }
}
