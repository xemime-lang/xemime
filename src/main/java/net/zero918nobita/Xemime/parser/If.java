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
        Node condition = new Expr(lexer, resolver).parse();
        ArrayList<Node> then = new ArrayList<>();
        ArrayList<Node> els = null;
        if (lexer.tokenType() == TokenType.LB) {
            getToken(); // skip "{"
            then.add(new Statement(lexer, resolver).parse());
            getToken();
            while (lexer.tokenType() != TokenType.RB) {
                then.add(new Statement(lexer, resolver).parse());
                getToken();
            }
            getToken();
            if (lexer.tokenType() == TokenType.ELSE) {
                getToken(); // skip ELSE
                if (lexer.tokenType() != TokenType.LB) throw new SyntaxError(lexer.getLocation(), 29, "");
                getToken(); // skip "{"
                els = new ArrayList<>();
                els.add(new Statement(lexer, resolver).parse());
                getToken();
                while (lexer.tokenType() != TokenType.RB) {
                    els.add(new Statement(lexer, resolver).parse());
                    getToken();
                }
                getToken();
            }
        } else {
            throw new SyntaxError(lexer.getLocation(), 27, "");
        }
        return new IfNode(lexer.getLocation(), condition, then, els);
    }
}
