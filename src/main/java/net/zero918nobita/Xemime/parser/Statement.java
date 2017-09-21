package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * ステートメントの構文解析を行います。
 * @author Kodai Matsumoto
 */

class Statement extends ParseUnit{
    Statement(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        Node node;

        switch (lexer.tokenType()) {
            case SWITCH:
                node = new SwitchStmt(lexer, resolver).parse();
                break;
            case FOR:
                node = new ForStmt(lexer, resolver).parse();
                break;
            case WHILE:
                node = new WhileStmt(lexer, resolver).parse();
                break;
            default:
                node = new Expr(lexer, resolver).parse();
        }

        // Syntax Error - ステートメントの末尾にはセミコロンが必要です。
        if (lexer.tokenType() != TokenType.SEMICOLON) throw new SyntaxError(lexer.getLocation(), 23, "ステートメントの末尾にはセミコロンが必要です。");

        return node;
    }
}
