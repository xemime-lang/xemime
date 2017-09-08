package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 項を構文解析します。
 * @author Kodai Matsumoto
 */

class Term extends ParseUnit {
    Term(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        Factor factor = new Factor(lexer, resolver);
        Node node = factor.parse();
        switch (tokenType) {
            case MUL:
            case DIV:
            case XOR:
                node = term(node);
                break;
        }
        return node;
    }

    private Node term(Node node) throws Exception {
        ExprNode result = null;
        while ((tokenType == TokenType.MUL) ||
                (tokenType == TokenType.DIV) ||
                (tokenType == TokenType.AND) ||
                (tokenType == TokenType.XOR)) {
            TokenType op = tokenType;
            getToken();
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
