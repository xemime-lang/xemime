package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

public class SwitchStmt extends ParseUnit {
    SwitchStmt(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        return null;
    }
}