package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * 引数リストの構文解析器
 * @author Kodai Matsumoto
 */

class Args extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    Args(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * この構文解析器は Node ではなく ArrayList&lt;Node&gt; を生成するため、このメソッドの使用を禁止しています。
     * @throws FatalError 常に発生させます。
     */
    @Override
    protected Node parse() throws FatalError {
        // Fatal Error - 呼び出し禁止メソッドの呼び出しが行われた
        throw new FatalError(lexer.getLocation(), 3);
    }

    /**
     * 引数リストの構文解析と意味解析を行います。
     * @return 生成された AST
     */
    ArrayList<Node> arguments() throws Exception {
        ArrayList<Node> list = null;
        if (lexer.tokenType() != TokenType.RP) {
            list = new ArrayList<>();
            list.add(new LogicalExpr(lexer, resolver).parse());
            while (lexer.tokenType() != TokenType.RP) {
                if (lexer.tokenType() != TokenType.COMMA) throw new Exception("文法エラーです");
                getToken();
                list.add(new LogicalExpr(lexer, resolver).parse());
            }
        }
        return list;
    }
}
