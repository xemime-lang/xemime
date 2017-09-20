package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.IfStmtNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * if 文の構文解析を行います。
 * @author Kodai Matsumoto
 */

class IfStmt extends ParseUnit {
    IfStmt(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        getToken();
        if (lexer.tokenType() != TokenType.LP) throw new SyntaxError(lexer.getLocation(), 24, "if 文の条件式を囲む括弧がありません。");
        getToken();
        Node condition = new Expr(lexer, resolver).parse();
        if (lexer.tokenType() != TokenType.RP) throw new SyntaxError(lexer.getLocation(), 25, "if 文の条件式の閉じ括弧がありません。");
        getToken();
        ArrayList<Node> body = new ArrayList<>();
        if (lexer.tokenType() == TokenType.LB) {
            Node then = new Block(lexer, resolver).parse();
            if (lexer.tokenType() == TokenType.SEMICOLON) {
                body.add(then);
            } else {
                throw new SyntaxError(lexer.getLocation(), 30, "");
            }
        } else {
            body.add(new Expr(lexer, resolver).parse());
        }
        return new IfStmtNode(lexer.getLocation(), condition, body, null);
    }
}
