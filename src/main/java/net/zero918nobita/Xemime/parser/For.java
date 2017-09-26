package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ForNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * for 文の構文解析を行います。
 * @author Kodai Matsumoto
 */

public class For extends ParseUnit {
    For(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        getToken(); // skip `for`
        ArrayList<Node> body = new ArrayList<>();

        // Syntax Error - for キーワードの後ろにはカウンタ変数を指定してください。
        if (lexer.tokenType() != TokenType.SYMBOL) throw new SyntaxError(lexer.getLocation(), 48, "for キーワードの後ろにはカウンタ変数を指定してください。");

        Symbol counter = (Symbol) lexer.value();
        getToken(); // skip symbol

        // Syntax Error - カウンタ変数の後ろには in キーワードを記述してください。
        if (lexer.tokenType() != TokenType.IN) throw new SyntaxError(lexer.getLocation(), 40, "カウンタ変数の後ろには in キーワードを記述してください。");

        getToken(); // skip `in`
        Node range = new LogicalExpr(lexer, resolver).parse();
        skipLineBreaks();

        // Syntax Error - `{` が必要です。
        if (lexer.tokenType() != TokenType.LB) throw new SyntaxError(lexer.getLocation(), 44, "");

        getToken(); // skip `{`
        resolver.declareVar(counter);
        while (lexer.tokenType() != TokenType.RB) {
            body.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken(); // skip `}`
        return new ForNode(lexer.getLocation(), counter, range, body);
    }
}
