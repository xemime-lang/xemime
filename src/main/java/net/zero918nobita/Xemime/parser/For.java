package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.ForNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.type.IntType;

import java.util.ArrayList;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

/**
 * for 文の構文解析器
 * @author Kodai Matsumoto
 */

public class For extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    For(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * for 分の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        getToken(); // skip `for`
        ArrayList<Node> body = new ArrayList<>();

        // Syntax Error - for キーワードの後ろにはカウンタ変数を指定してください。
        if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 48, "for キーワードの後ろにはカウンタ変数を指定してください。");

        Symbol counter = (Symbol) lexer.value();
        getToken(); // skip symbol

        // Syntax Error - カウンタ変数の後ろには in キーワードを記述してください。
        if (!current(IN)) throw new SyntaxError(lexer.getLocation(), 40, "カウンタ変数の後ろには in キーワードを記述してください。");

        getToken(); // skip `in`
        Node range = new LogicalExpr(lexer, resolver).parse();
        skipLineBreaks();

        // Syntax Error - 範囲式の後ろに波括弧 `{` を記述してください。
        if (!current(LB)) throw new SyntaxError(lexer.getLocation(), 44, "範囲式の後ろに `{` を記述してください。");

        getToken(); // skip `{`
        resolver.declareVar(new IntType(), counter);
        while (!current(RB)) {
            body.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken(); // skip `}`
        return new ForNode(lexer.getLocation(), counter, range, body);
    }
}
