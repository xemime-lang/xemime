package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.LambdaExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * ラムダ式を構文解析します。
 * @author Kodai Matsumoto
 */

class Lambda extends ParseUnit {
    Lambda(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        ArrayList<Symbol> list = new ArrayList<>();
        getToken();
        if (tokenType == TokenType.SYMBOL) {
            list.add((Symbol) lexer.value());
            getToken();
            while (tokenType != TokenType.ARROW) {
                if (tokenType != TokenType.COMMA) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                getToken();
                if (tokenType != TokenType.SYMBOL) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                list.add((Symbol) lexer.value());
                getToken();
            }
        } else if (tokenType == TokenType.LP) {
            getToken();
            if (tokenType == TokenType.SYMBOL) {
                list.add((Symbol) lexer.value());
                getToken();
                while (tokenType != TokenType.RP) {
                    if (tokenType != TokenType.COMMA) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                    getToken();
                    if (tokenType != TokenType.SYMBOL) throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数が不正です");
                    list.add((Symbol) lexer.value());
                    getToken();
                }
            }
            getToken();
            if (tokenType != TokenType.ARROW) throw new Exception(lexer.getLocation() + ": ラムダ式のアロー演算子がありません");
        } else if (tokenType != TokenType.ARROW) {
            throw new Exception(lexer.getLocation() + ": ラムダ式の仮引数リストまたはアロー演算子が必要です");
        }
        getToken();
        resolver.addScope();
        for (Symbol sym : list) resolver.declareVar(sym);
        Node expr = new Expr(lexer, resolver).parse();
        resolver.removeScope();
        return new LambdaExprNode(lexer.getLocation(), list, expr);
    }
}
