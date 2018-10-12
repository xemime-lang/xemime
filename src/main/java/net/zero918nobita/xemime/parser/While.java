package net.zero918nobita.xemime.parser;

import net.zero918nobita.xemime.ast.Node;
import net.zero918nobita.xemime.ast.WhileNode;
import net.zero918nobita.xemime.lexer.Lexer;
import net.zero918nobita.xemime.resolver.Resolver;

import java.util.ArrayList;

import static net.zero918nobita.xemime.lexer.TokenType.LB;
import static net.zero918nobita.xemime.lexer.TokenType.RB;

/**
 * while 文の構文解析器
 * @author Kodai Matsumoto
 */

public class While extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    While(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * while 文の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        getToken(); // skip `while``
        ArrayList<Node> body = new ArrayList<>();
        Node condition = new LogicalExpr(lexer, resolver).parse();

        // Syntax Error - 条件式の後ろに波括弧 `{` を記述してください。
        if (!current(LB)) throw new SyntaxError(lexer.getLocation(), 45, "条件式の後ろに波括弧 `{` を記述してください。");

        getToken(); // skip `{`
        while (!current(RB)) {
            body.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken();
        return new WhileNode(lexer.getLocation(), condition, body);
    }
}
