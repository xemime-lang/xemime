package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * 因子を構文解析します。
 * @author Kodai Matsumoto
 */

class Factor extends ParseUnit {
    Factor(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        Node node;
        switch (lexer.tokenType()) {
            case STRING:
                node = lexer.value();
                getToken();
                break;
            case T:
                node = Bool.T;
                getToken();
                break;
            case NIL:
                node = Bool.Nil;
                getToken();
                break;
            case NOT:
                getToken();
                node = new NotNode(lexer.getLocation(), new Factor(lexer, resolver).parse());
                break;
            case LAMBDA:
                node = new Lambda(lexer, resolver).parse();
                break;
            default:
                node = new First(lexer, resolver).parse();
        }

        while (lexer.tokenType() == TokenType.DOLLAR ||
                lexer.tokenType() == TokenType.PERIOD ||
                lexer.tokenType() == TokenType.LP) {
            switch (lexer.tokenType()) {
                case DOLLAR: {
                    getToken();
                    ArrayList<Node> list = new ArrayList<>();
                    if (lexer.tokenType() == TokenType.SEMICOLON) {
                        node = new FuncallNode(lexer.getLocation(), node, list);
                        break;
                    }
                    Node expr = new LogicalExpr(lexer, resolver).parse();
                    if (expr == null) throw new Exception(lexer.getLocation() + ": 文法エラーです");
                    list.add(expr);
                    while (lexer.tokenType() != TokenType.BR &&
                            lexer.tokenType() != TokenType.EOS &&
                            lexer.tokenType() != TokenType.SEMICOLON) {
                        if (lexer.tokenType() != TokenType.COMMA)
                            throw new Exception(lexer.getLocation() + ": 文法エラーです");
                        getToken();
                        list.add(new LogicalExpr(lexer, resolver).parse());
                    }
                    node = new FuncallNode(lexer.getLocation(), node, list);
                    break;
                }
                case LP: {
                    ArrayList<Node> list = new ArrayList<>();
                    getToken();
                    if (lexer.tokenType() != TokenType.RP) list = new Args(lexer, resolver).arguments();
                    if (lexer.tokenType() != TokenType.RP) throw new Exception(lexer.getLocation() + ": 文法エラー");
                    getToken();
                    node = new FuncallNode(lexer.getLocation(), node, list);
                    break;
                }
                case PERIOD:
                    getToken();

                    // SyntaxError - メッセージ式のピリオドの後ろがシンボルではありません。
                    if (lexer.tokenType() != TokenType.SYMBOL)
                        throw new SyntaxError(lexer.getLocation(), 4, "メッセージ式のピリオドの後ろがシンボルではありません。");

                    Symbol sym = (Symbol) lexer.value();
                    getToken();
                    if (lexer.tokenType() == TokenType.LP) {
                        ArrayList<Node> list = new ArrayList<>();
                        getToken();
                        if (lexer.tokenType() != TokenType.RP) list = new Args(lexer, resolver).arguments();

                        // SyntaxError - メッセージ式の括弧が閉じられていません。
                        if (lexer.tokenType() != TokenType.RP)
                            throw new SyntaxError(lexer.getLocation(), 5, "メッセージ式の括弧が閉じられていません。");

                        getToken();
                        node = new DotCallNode(lexer.getLocation(), node, sym, list);
                    } else if (lexer.tokenType() == TokenType.ASSIGN) {
                        getToken();
                        Node c = new LogicalExpr(lexer, resolver).parse();
                        node = new DotAssignNode(lexer.getLocation(), node, sym, c);
                    } else {
                        node = new DotExprNode(lexer.getLocation(), node, sym);
                    }
                    break;
            }
        }
        return node;
    }
}
