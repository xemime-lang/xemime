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
    Node parse() throws Exception {
        ArrayList<Node> list = null;
        getToken();
        resolver.addScope();
        while (tokenType != TokenType.RB) {
            Node node = new Expr(lexer, resolver).parse();
            if (tokenType == TokenType.SEMICOLON) {
                if (list == null) list = new ArrayList<>();
                list.add(node);
            } else {
                throw new Exception("文法エラーです");
            }
            getToken();
        }
        resolver.removeScope();
        getToken();
        return new BlockNode(lexer.getLocation(), list);
    }
}
