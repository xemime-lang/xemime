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

    /**
     * 結果が ArrayList&lt;Node&gt; となる構文解析を行うので、 Node を返すこのメソッドの使用を禁止しています。
     * @throws FatalError 常に発生させます。
     */
    @Override
    protected Node parse() throws FatalError {
        // Fatal Error - 呼び出し禁止メソッドの呼び出しが行われた
        throw new FatalError(lexer.getLocation(), 3);
    }

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
