package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;

class Statement extends ParseUnit{
    Statement(Lexer lexer) {
        super(lexer);
    }

    @Override
    Node parse() throws Exception {
        Expr expr = new Expr(lexer);
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
