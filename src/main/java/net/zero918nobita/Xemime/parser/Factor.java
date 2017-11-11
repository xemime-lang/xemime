package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.resolver.TypeError;
import net.zero918nobita.Xemime.type.BoolType;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

/**
 * 因子の構文解析器
 * @author Kodai Matsumoto
 */

class Factor extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    Factor(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * 因子の構文解析と意味解析を行います。
     * @return 生成された AST
     */
    @Override
    protected Node parse() throws Exception {
        Node node;
        switch (lexer.tokenType()) {
            // 文字列定数
            case STRING:
                node = lexer.value();
                getToken();
                break;

            // 真値
            case T:
                node = Bool.T;
                getToken();
                break;

            // 偽値
            case NIL:
                node = Bool.Nil;
                getToken();
                break;

            // 論理否定
            case NOT:
                getToken();
                Node modified = new Factor(lexer, resolver).parse();

                // Type Error - 論理否定演算子を真偽型以外の型のデータに適用することはできません。
                if (!(resolver.getTypeOfNode(modified) instanceof BoolType)) throw new TypeError(modified.getLocation(), 56, "論理否定演算子を真偽型以外の型のデータに適用することはできません。");

                node = new NotNode(lexer.getLocation(), modified);
                break;

            // ラムダ式
            case LAMBDA:
                node = new Lambda(lexer, resolver).parse();
                break;

            // 一次子
            default:
                node = new First(lexer, resolver).parse();
        }

        while (current(DOLLAR) || current(PERIOD) || current(LP)) {
            switch (lexer.tokenType()) {
                // ドルマークを使用し括弧を省略した関数呼び出し
                case DOLLAR: {
                    getToken();
                    ArrayList<Node> list = new ArrayList<>();
                    if (current(SEMICOLON)) {
                        node = new FuncallNode(lexer.getLocation(), node, list);
                        break;
                    }
                    if (!current(BR) && !current(EOS) && !current(SEMICOLON)) {
                        Node expr = new LogicalExpr(lexer, resolver).parse();
                        if (expr == null) throw new SyntaxError(lexer.getLocation(), 68, "引数リストが不正です。");
                        list.add(expr);
                        while (!current(BR) && !current(EOS) && !current(SEMICOLON)) {
                            if (!current(COMMA))
                                throw new SyntaxError(lexer.getLocation(), 69, "引数をコンマで区切ってください。");
                            getToken();
                            list.add(new LogicalExpr(lexer, resolver).parse());
                        }
                    }
                    node = new FuncallNode(lexer.getLocation(), node, list);
                    break;
                    /*LinkedHashMap<Symbol, Node> map = null;
                    ArrayList<Node> arrayList = null;
                    getToken(); // skip `$`
                    if (!current(BR) && !current(EOS) && !current(SEMICOLON)) {
                        Node tmp = lexer.value();
                        getToken();
                        if (current(COLON)) {
                            // 名前付き引数を有効にする
                            getToken(); // skip `:`
                            map = new LinkedHashMap<>();
                            map.put((Symbol)tmp, new LogicalExpr(lexer, resolver).parse());

                            Symbol label;

                            while (!current(BR) && !current(EOS)) {
                                if (!current(COMMA)) throw new Exception("文法エラーです");
                                getToken(); // skip `,`
                                skipLineBreaks();
                                if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 69, "");
                                label = (Symbol)lexer.value();
                                getToken(); // skip symbol
                                if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 86, "");
                                getToken(); // skip `:`
                                map.put(label, new LogicalExpr(lexer, resolver).parse());
                            }
                        } else {
                            // 名前付き引数を無効にする
                            arrayList = new ArrayList<>();
                            arrayList.add(new LogicalExpr(lexer, resolver).parse());

                            while (!current(BR) && !current(EOS)) {
                                if (!current(COMMA)) throw new Exception("文法エラーです");
                                getToken(); // skip `,`
                                skipLineBreaks();
                                arrayList.add(new LogicalExpr(lexer, resolver).parse());
                            }
                        }
                    }
                    if (map != null) {
                        node = new FuncallNode(lexer.getLocation(), node, map);
                    } else {
                        node = new FuncallNode(lexer.getLocation(), node, arrayList);
                    }
                    break;*/
                }

                // 関数呼び出し
                case LP: {
                    LinkedHashMap<Symbol, Node> list = new LinkedHashMap<>();
                    getToken();
                    if (!current(RP)) list = new Args(lexer, resolver).arguments();
                    if (!current(RP)) throw new Exception(lexer.getLocation() + ": 文法エラー");
                    getToken();
                    node = new FuncallNode(lexer.getLocation(), node, list);
                    break;
                }

                // メッセージ式
                case PERIOD:
                    getToken();

                    // SyntaxError - メッセージ式のピリオドの後ろがシンボルではありません。
                    if (!current(SYMBOL))
                        throw new SyntaxError(lexer.getLocation(), 4, "メッセージ式のピリオドの後ろがシンボルではありません。");

                    Symbol sym = (Symbol) lexer.value();
                    getToken();
                    if (current(LP)) {
                        LinkedHashMap<Symbol, Node> list = new LinkedHashMap<>();
                        getToken();
                        if (!current(RP)) list = new Args(lexer, resolver).arguments();

                        // SyntaxError - メッセージ式の括弧が閉じられていません。
                        if (!current(RP))
                            throw new SyntaxError(lexer.getLocation(), 5, "メッセージ式の括弧が閉じられていません。");

                        getToken();
                        node = new DotCallNode(lexer.getLocation(), node, sym, list);
                    } else if (current(ASSIGN)) {
                        getToken();
                        Node c = new LogicalExpr(lexer, resolver).parse();
                        node = new DotAssignNode(lexer.getLocation(), node, sym, c);
                    } else {
                        node = new DotExprNode(lexer.getLocation(), node, sym);
                    }
                    break;
            }
        }

        switch (lexer.tokenType()) {
            // 範囲式 ( 最大値を持つ )
            case RANGE2: {
                Node left = node;
                getToken();
                Node right = new Expr(lexer, resolver).parse();
                node = new RangeExprNode(lexer.getLocation(), left, right, true);
                break;
            }

            // 範囲式 ( 最大値を持たない )
            case RANGE3: {
                Node left = node;
                getToken();
                Node right = new Expr(lexer, resolver).parse();
                node = new RangeExprNode(lexer.getLocation(), left, right, false);
                break;
            }
        }

        return node;
    }
}
