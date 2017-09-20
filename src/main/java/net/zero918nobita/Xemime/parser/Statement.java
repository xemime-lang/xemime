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
            case IF:
                node = new IfStmt(lexer, resolver).parse();
                if (lexer.tokenType() != TokenType.SEMICOLON) throw new SyntaxError(lexer.getLocation(), 26, "");
                break;
            case SWITCH:
                node = new SwitchStmt(lexer, resolver).parse();
                if (lexer.tokenType() != TokenType.SEMICOLON) throw new SyntaxError(lexer.getLocation(), 27, "");
                break;
            case FOR:
                node = new ForStmt(lexer, resolver).parse();
                if (lexer.tokenType() != TokenType.SEMICOLON) throw new SyntaxError(lexer.getLocation(), 28, "");
                break;
            case WHILE:
                node = new WhileStmt(lexer, resolver).parse();
                if (lexer.tokenType() != TokenType.SEMICOLON) throw new SyntaxError(lexer.getLocation(), 29, "");
                break;
            default:
                node = new Expr(lexer, resolver).parse();
                if (lexer.tokenType() != TokenType.SEMICOLON) throw new SyntaxError(lexer.getLocation(), 23, "式の末尾にはセミコロンが必要です。");
        }

        return node;
    }
}
