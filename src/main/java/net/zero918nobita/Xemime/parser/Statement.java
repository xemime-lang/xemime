package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

class Statement extends ParseUnit{
    Statement(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        Expr expr = new Expr(lexer, resolver);
        Node node = expr.parse();
        if (node != null)
            switch (tokenType) {
                case SEMICOLON:
                    break;
                default:
                    throw new Exception(lexer.getLocation() + ": 文法エラーです");
            }
        return node;
    }
}
