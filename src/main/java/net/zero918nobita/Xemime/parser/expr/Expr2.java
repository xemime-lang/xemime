package net.zero918nobita.Xemime.parser.expr;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.parser.ParseUnit;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 関数呼び出し式の構文解析を行います。
 * @author Kodai Matsumoto
 */

public class Expr2 extends ParseUnit {
    public Expr2(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    public Node parse() throws Exception {
        return null;
    }
}
