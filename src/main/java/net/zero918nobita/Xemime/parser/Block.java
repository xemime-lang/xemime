package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.BlockNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * ブロック式を構文解析します。
 * @author Kodai Matsumoto
 */

class Block extends ParseUnit {
    Block(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        ArrayList<Node> list;
        getToken();
        resolver.addScope();
        while (lexer.tokenType() == TokenType.BR) getToken();
        Node node = new Expr(lexer, resolver).parse();
        if (lexer.tokenType() == TokenType.BR || lexer.tokenType() == TokenType.RB) {
            list = new ArrayList<>();
            list.add(node);
        } else {
            throw new SyntaxError(lexer.getLocation(), 31, "");
        }
        while (lexer.tokenType() != TokenType.RB) {
            while (lexer.tokenType() == TokenType.BR) getToken();
            if (lexer.tokenType() == TokenType.RB) break;
            node = new Expr(lexer, resolver).parse();
            if (lexer.tokenType() == TokenType.BR || lexer.tokenType() == TokenType.RB) {
                list.add(node);
            } else {
                // Syntax Error - ブロック式内のステートメントにセミコロンが付いていません。
                throw new SyntaxError(lexer.getLocation(), 7, "ブロック式内のステートメントにセミコロンが付いていません。");
            }
            getToken();
        }
        resolver.removeScope();
        getToken();
        return new BlockNode(lexer.getLocation(), list);
    }
}
