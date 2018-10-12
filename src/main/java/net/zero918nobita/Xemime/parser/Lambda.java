package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.LambdaExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.type.AnyType;

import java.util.ArrayList;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

/**
 * ラムダ式の構文解析器
 * @author Kodai Matsumoto
 */

class Lambda extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    Lambda(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * ラムダ式の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        ArrayList<Symbol> list = new ArrayList<>();
        getToken();
        if (current(SYMBOL)) {
            list.add((Symbol) lexer.value());
            getToken();
            while (!current(ARROW)) {
                if (!current(COMMA)) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                getToken();
                if (!current(SYMBOL)) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                list.add((Symbol) lexer.value());
                getToken();
            }
        } else if (current(LP)) {
            getToken();
            if (current(SYMBOL)) {
                list.add((Symbol) lexer.value());
                getToken();
                while (!current(RP)) {
                    if (!current(COMMA)) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                    getToken();
                    if (!current(SYMBOL)) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                    list.add((Symbol) lexer.value());
                    getToken();
                }
            }
            getToken();
            if (!current(ARROW)) throw new Exception(lexer.getLocation() + ": ラムダ式のアロー演算子がありません");
        } else if (!current(ARROW)) {
            throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数リストまたはアロー演算子が必要です");
        }
        getToken();
        resolver.addScope();
        for (Symbol sym : list) resolver.declareVar(new AnyType(), sym);
        Node expr = new LogicalExpr(lexer, resolver).parse();
        resolver.removeScope();
        return new LambdaExprNode(lexer.getLocation(), list, expr);
    }
}
