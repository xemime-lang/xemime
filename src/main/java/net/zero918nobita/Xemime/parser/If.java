package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.IfNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * if 文の構文解析を行います。
 * @author Kodai Matsumoto
 */

class If extends ParseUnit {
    If(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        getToken(); // skip IF
        Node condition = new LogicalExpr(lexer, resolver).parse();
        ArrayList<Node> then = new ArrayList<>();
        ArrayList<Node> els = null;

        // Syntax Error - 条件式の後ろに波括弧 `{` を記述してください。
        if (lexer.tokenType() != TokenType.LB) throw new SyntaxError(lexer.getLocation(), 27, "");

        getToken(); // skip "{"
        while (lexer.tokenType() != TokenType.RB) {
            then.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken();
        skipLineBreaks();
        if (lexer.tokenType() == TokenType.ELSE) {
            getToken();
            skipLineBreaks();

            // Syntax Error - `else` の後ろに波括弧 `{` を記述してください。
            if (lexer.tokenType() != TokenType.LB) throw new SyntaxError(lexer.getLocation(), 29, "");

            getToken(); // skip "{"
            els = new ArrayList<>();
            while (lexer.tokenType() != TokenType.RB) {
                els.add(new Expr(lexer, resolver).parse());
                skipLineBreaks();
            }
            getToken();
        }
        getToken();
        return new IfNode(lexer.getLocation(), condition, then, els);
    }
}
