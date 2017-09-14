package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
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
        Node node = new Expr(lexer, resolver).parse();
        if (node != null)
            switch (lexer.tokenType()) {
                case SEMICOLON:
                    break;
                default:
                    throw new Exception(lexer.getLocation() + ": 文法エラーです");
            }
        return node;
    }
}
