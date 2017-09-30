package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 項の構文解析器
 * @author Kodai Matsumoto
 */

class Term extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    Term(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * 項の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        Factor factor = new Factor(lexer, resolver);
        Node node = factor.parse();
        switch (lexer.tokenType()) {
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
        while ((lexer.tokenType() == TokenType.MUL) ||
                (lexer.tokenType() == TokenType.DIV) ||
                (lexer.tokenType() == TokenType.XOR)) {
            TokenType op = lexer.tokenType();
            getToken();
            skipLineBreaks();
            Node term = new Term(lexer, resolver).parse();

            // DivideByZeroError - 構文解析中にゼロ除算が行われている箇所を発見しました。
            if (op == TokenType.DIV && term.equals(new Int(0, 0))) throw new DivideByZeroError(lexer.getLocation(), 1);

            if (result == null) {
                result = new ExprNode(lexer.getLocation(), op, node, term);
                resolver.getTypeOfNode(result);
            } else {
                result = new ExprNode(lexer.getLocation(), op, result, term);
                resolver.getTypeOfNode(result);
            }
        }
        return result;
    }
}
