package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.WhileNode;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * while 文の構文解析器
 * @author Kodai Matsumoto
 */

public class While extends ParseUnit {
    While(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        getToken(); // skip `while``
        ArrayList<Node> body = new ArrayList<>();
        Node condition = new LogicalExpr(lexer, resolver).parse();

        // Syntax Error - 条件式の後ろに波括弧 `{` を記述してください。
        if (lexer.tokenType() != TokenType.LB) throw new SyntaxError(lexer.getLocation(), 45, "条件式の後ろに波括弧 `{` を記述してください。");

        getToken(); // skip `{`
        while (lexer.tokenType() != TokenType.RB) {
            body.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken();
        return new WhileNode(lexer.getLocation(), condition, body);
    }
}
