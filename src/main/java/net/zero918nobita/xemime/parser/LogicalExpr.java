package net.zero918nobita.xemime.parser;

import net.zero918nobita.xemime.ast.ExprNode;
import net.zero918nobita.xemime.ast.Node;
import net.zero918nobita.xemime.lexer.Lexer;
import net.zero918nobita.xemime.lexer.TokenType;
import net.zero918nobita.xemime.resolver.Resolver;

import static net.zero918nobita.xemime.lexer.TokenType.*;

/**
 * 論理式の構文解析器
 * @author Kodai Matsumoto
 */

class LogicalExpr extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    LogicalExpr(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * 論理式の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        if (current(IF)) return new If(lexer, resolver).parse();
        Node node = new ArithmeticExpr(lexer, resolver).parse();
        switch (lexer.tokenType()) {
            case AND:
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
        while ((current(AND)) ||
                (current(L)) ||
                (current(G)) ||
                (current(EQ)) ||
                (current(EQL)) ||
                (current(NE)) ||
                (current(LE)) ||
                (current(GE))) {
            TokenType op = lexer.tokenType();
            getToken();
            Node simpleExpr = new ArithmeticExpr(lexer, resolver).parse();
            if (result == null) result = new ExprNode(lexer.getLocation(), op, node, simpleExpr);
            else result = new ExprNode(lexer.getLocation(), op, result, simpleExpr);
        }
        return result;
    }
}
