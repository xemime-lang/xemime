package net.zero918nobita.Xemime.parser.expr;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.parser.ParseUnit;
import net.zero918nobita.Xemime.parser.SyntaxError;
import net.zero918nobita.Xemime.resolver.Resolver;

/**
 * 式 ( 優先度: 2 ) の構文解析を行います。
 * @author Kodai Matsumoto
 */

/*
 * expr2 = INT
 *     | DOUBLE
 *     | UNIT
 *     | SYMBOL [ ( '++' | '--' | '=' , expr ) ]
 *     | ( '+' | '-' | '!' ) , expr
 *     | ( '++' | '--' ) , SYMBOL
 *     | '(' , expr , ')'
 *     ;
 */

public class Expr2 extends ParseUnit {
    public Expr2(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    public Node parse() throws Exception {
        Node node = null;
        switch (lexer.tokenType()) {
            // ソースコードの末端
            case EOS:
                break;

            // unit 値 (無効な値)
            case UNIT:
                node = new Unit(lexer.getLocation(), null);
                break;

            // 整数値 または 小数値
            case INT:
            case DOUBLE:
                node = lexer.value();
                getToken();
                break;

            // 単項演算子 `+`
            case ADD:
                getToken();
                node = new Expr(lexer, resolver).parse();
                break;

            // 単項演算子 `-`
            case SUB:
                getToken();
                node = new MinusNode(lexer.getLocation(), new Expr(lexer, resolver).parse());
                break;

            // 前置インクリメント
            case INCREMENT:
                getToken();
                if (lexer.tokenType() != TokenType.SYMBOL) throw new SyntaxError(lexer.getLocation(), 32, "");
                node = new PrefixIncrementNode(lexer.getLocation(), (Symbol) lexer.value());
                break;

            // 前置デクリメント
            case DECREMENT:
                getToken();
                if (lexer.tokenType() != TokenType.SYMBOL) throw new SyntaxError(lexer.getLocation(), 33, "");
                node = new PrefixDecrementNode(lexer.getLocation(), (Symbol) lexer.value());
                break;

            case SYMBOL:
                Symbol sym = (Symbol) lexer.value();
                getToken();
                switch (lexer.tokenType()) {
                    case INCREMENT:
                        node = new SuffixIncrementNode(lexer.getLocation(), sym);
                        break;
                    case DECREMENT:
                        node = new SuffixDecrementNode(lexer.getLocation(), sym);
                        break;
                    default:
                        throw new SyntaxError(lexer.getLocation(), 38, "");
                }
                break;

            default:
                throw new SyntaxError(lexer.getLocation(), 39, "");
        }
        return node;
    }
}
