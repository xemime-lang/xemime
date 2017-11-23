package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.IfNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

import static net.zero918nobita.Xemime.lexer.TokenType.ELSE;
import static net.zero918nobita.Xemime.lexer.TokenType.LB;
import static net.zero918nobita.Xemime.lexer.TokenType.RB;

/**
 * if 文の構文解析器
 * @author Kodai Matsumoto
 */

class If extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    If(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * if 文の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        getToken(); // skip IF
        Node condition = new LogicalExpr(lexer, resolver).parse();
        ArrayList<Node> then = new ArrayList<>();
        ArrayList<Node> els = null;

        // Syntax Error - 条件式の後ろに波括弧 `{` を記述してください。
        if (!current(LB)) throw new SyntaxError(lexer.getLocation(), 27, "");

        getToken(); // skip "{"
        while (!current(RB)) {
            then.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken();
        skipLineBreaks();
        if (current(ELSE)) {
            getToken();
            skipLineBreaks();

            // Syntax Error - `else` の後ろに波括弧 `{` を記述してください。
            if (!current(LB)) throw new SyntaxError(lexer.getLocation(), 29, "");

            getToken(); // skip "{"
            els = new ArrayList<>();
            while (!current(RB)) {
                els.add(new Expr(lexer, resolver).parse());
                skipLineBreaks();
            }
            getToken();
        }
        return new IfNode(lexer.getLocation(), condition, then, els);
    }
}
