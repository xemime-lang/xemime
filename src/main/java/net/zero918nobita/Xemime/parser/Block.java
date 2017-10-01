package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.BlockNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

import static net.zero918nobita.Xemime.lexer.TokenType.BR;
import static net.zero918nobita.Xemime.lexer.TokenType.RB;

/**
 * ブロック式の構文解析器
 * @author Kodai Matsumoto
 */

class Block extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    Block(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * ブロック式の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        ArrayList<Node> list;
        getToken();
        resolver.addScope();
        skipLineBreaks();
        Node node = new Expr(lexer, resolver).parse();

        // Syntax Error - 不明なトークン [value] が発見されました。
        if (!current(BR) && !current(RB)) throw new SyntaxError(lexer.getLocation(), 31, "不明なトークン `" + lexer.value() + "` が発見されました。");

        list = new ArrayList<>();
        list.add(node);
        while (!current(RB)) {
            skipLineBreaks();
            if (current(RB)) break;
            node = new Expr(lexer, resolver).parse();
            if (current(BR) || current(RB)) {
                list.add(node);
            } else {
                // Syntax Error - ブロック式内のステートメントにセミコロンが付いていません。
                throw new SyntaxError(lexer.getLocation(), 7, "ブロック式内のステートメントにセミコロンが付いていません。");
            }
            getToken();
        }
        resolver.removeScope();
        getToken();
        return new BlockNode(lexer.getLocation(), list);
    }
}
