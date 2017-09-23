package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 単純式の構文解析を行います。
 * @author Kodai Matsumoto
 */

class SimpleExpr extends ParseUnit {
    SimpleExpr(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        Node node = new Term(lexer, resolver).parse();
        switch (lexer.tokenType()) {
            case ADD:
            case SUB:
            case OR:
                node = simpleExpr(node);
                break;
        }
        return node;
    }

    private Node simpleExpr(Node node) throws Exception {
        ExprNode result = null;
        while ((lexer.tokenType() == TokenType.ADD) ||
                (lexer.tokenType() == TokenType.SUB) ||
                (lexer.tokenType() == TokenType.OR)) {
            TokenType op = lexer.tokenType();
            getToken();
            while (lexer.tokenType() == TokenType.BR) getToken();
            Node term = new Term(lexer, resolver).parse();
            if (result == null) {
                result = new ExprNode(lexer.getLocation(), op, node, term);
            } else {
                result = new ExprNode(lexer.getLocation(), op, result, term);
            }
        }
        return result;
    }
}
