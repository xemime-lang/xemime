package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

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
        if (!current(RP)) {
            list = new ArrayList<>();
            list.add(new LogicalExpr(lexer, resolver).parse());
            while (!current(RP)) {
                if (!current(COMMA)) throw new SyntaxError(lexer.getLocation(), 93, "引数をコンマで区切ってください。");
                getToken();
                list.add(new LogicalExpr(lexer, resolver).parse());
            }
        }
        return list;

        /*LinkedHashMap<Symbol, Node> list = null;

        if (!current(RP)) {
            list = new LinkedHashMap<>();

            if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 82, "");
            Symbol label = (Symbol) lexer.value();
            getToken(); // skip symbol
            if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 81, "");
            getToken(); // skip `:`
            list.put(label, new LogicalExpr(lexer, resolver).parse());

            while (!current(RP)) {
                if (!current(COMMA)) throw new Exception("文法エラーです");
                getToken(); // skip `,`
                skipLineBreaks();
                if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 83, "");
                label = (Symbol) lexer.value();
                getToken(); // skip symbol
                if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 84, "");
                getToken(); // skip `:`
                list.put(label, new LogicalExpr(lexer, resolver).parse());
            }
        }
        return list;*/
    }
}
