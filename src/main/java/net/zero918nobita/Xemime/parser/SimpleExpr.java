package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;

class SimpleExpr extends ParseUnit {
    SimpleExpr(Lexer lexer) {
        super(lexer);
    }

    @Override
    Node parse() throws Exception {
        Term term = new Term(lexer);
        Node node = term.parse();
        switch (tokenType) {
            case ADD:
            case SUB:
            case OR:
                node = arithmeticExpr(node);
                break;
        }
        return node;
    }

    private Node arithmeticExpr(Node node) throws Exception {
        ExprNode result = null;
        while ((tokenType == TokenType.ADD) ||
                (tokenType == TokenType.SUB) ||
                (tokenType == TokenType.OR)) {
            TokenType op = tokenType;
            getToken();
            Node term = new Term(lexer).parse();
            if (result == null) {
                result = new ExprNode(lexer.getLocation(), op, node, term);
            } else {
                result = new ExprNode(lexer.getLocation(), op, result, term);
            }
        }
        return result;
    }
}
