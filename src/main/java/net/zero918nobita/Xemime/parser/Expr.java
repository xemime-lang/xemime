package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;

class Expr extends ParseUnit {
    Expr(Lexer lexer) {
        super(lexer);
    }

    @Override
    Node parse() throws Exception {
        SimpleExpr simpleExpr = new SimpleExpr(lexer);
        Node node = simpleExpr.parse();
        switch (tokenType) {
            case L:
            case G:
            case EQ:
            case EQL:
            case NE:
            case LE:
            case GE:
                node = logicalExpr(node);
                break;
        }
        return node;
    }

    private Node logicalExpr(Node node) throws Exception {
        ExprNode result = null;
        while ((tokenType == TokenType.L) ||
                (tokenType == TokenType.G) ||
                (tokenType == TokenType.EQ) ||
                (tokenType == TokenType.EQL) ||
                (tokenType == TokenType.NE) ||
                (tokenType == TokenType.LE) ||
                (tokenType == TokenType.GE)) {
            TokenType op = tokenType;
            getToken();
            Node simpleExpr = new SimpleExpr(lexer).parse();
            if (result == null) result = new ExprNode(lexer.getLocation(), op, node, simpleExpr);
            else result = new ExprNode(lexer.getLocation(), op, result, simpleExpr);
        }
        return result;
    }
}