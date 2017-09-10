package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * 引数リストの構文解析を行います。
 * @author Kodai Matsumoto
 */

class Args extends ParseUnit {
    Args(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        throw new Exception("引数リストは単一のノードに変換できません");
    }

    ArrayList<Node> arguments() throws Exception {
        ArrayList<Node> list = null;
        if (lexer.tokenType() != TokenType.RP) {
            list = new ArrayList<>();
            list.add(new Expr(lexer, resolver).parse());
            while (lexer.tokenType() != TokenType.RP) {
                if (lexer.tokenType() != TokenType.COMMA) throw new Exception("文法エラーです");
                getToken();
                list.add(new Expr(lexer, resolver).parse());
            }
        }
        return list;
    }
}
