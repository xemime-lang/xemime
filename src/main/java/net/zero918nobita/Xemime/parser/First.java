package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * 一次子の構文解析を行います。
 * @author Kodai Matsumoto
 */

class First extends ParseUnit {
    First(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        Node node = null;
        switch (lexer.tokenType()) {
            case EOS:
                break;
            case INT:
                node = lexer.value();
                getToken(); // skip int literal
                break;
            case DOUBLE:
                node = lexer.value();
                getToken(); // skip double literal
                break;
            case SUB:
                getToken(); // skip "-"
                node = new MinusNode(lexer.getLocation(), new First(lexer, resolver).parse());
                break;
            case LP:
                getToken(); // skip "("
                node = new Expr(lexer, resolver).parse();

                // Syntax Error - 対応する括弧がありません。
                if (lexer.tokenType() != TokenType.RP) throw new SyntaxError(lexer.getLocation(), 8, "対応する括弧がありません。");

                getToken(); // skip ")"
                break;
            case LB:
                node = new Block(lexer, resolver).parse();
                break;
            case DECLARE:
                getToken(); // skip "let"
                if (lexer.tokenType() == TokenType.SYMBOL) {
                    Symbol sym = (Symbol) lexer.value();
                    getToken(); // skip symbol
                    if (lexer.tokenType() == TokenType.ASSIGN) {
                        getToken(); // skip "="
                        // 現在のスコープに変数を登録する
                        resolver.declareVar(sym);
                        node = new DeclarationNode(lexer.getLocation(), sym, new Expr(lexer, resolver).parse());
                    } else {
                        throw new Exception(lexer.getLocation() + ": 変数宣言式が不正です");
                    }
                } else {
                    throw new Exception(lexer.getLocation() + ": 変数宣言式が不正です");
                }
                break;
            case SUBST:
                getToken(); // skip "subst"
                if (lexer.tokenType() == TokenType.SYMBOL) {
                    Symbol sym = (Symbol) lexer.value();
                    getToken(); // skip symbol
                    if (lexer.tokenType() == TokenType.ATTACH) {
                        getToken(); // skip "<-"
                        node = new SubstanceDeclarationNode(lexer.getLocation(), sym, new Expr(lexer, resolver).parse());
                    } else {
                        throw new Exception(lexer.getLocation() + ": 実体宣言式が不正です。");
                    }
                } else {
                    throw new Exception(lexer.getLocation() + ": 実体宣言式が不正です。");
                }
            case SYMBOL:
                Symbol sym = (Symbol) lexer.value();
                // 変数の参照を解決する
                resolver.referVar(lexer.getLocation(), sym);
                getToken(); // skip symbol
                if (lexer.tokenType() == TokenType.ASSIGN) {
                    // 宣言済みの変数への代入
                    getToken();
                    node = new AssignNode(lexer.getLocation(), sym, new Expr(lexer, resolver).parse());
                } else if (lexer.tokenType() == TokenType.LP) {
                    // 関数呼び出し
                    node = new MethodCall(lexer, resolver).methodCall(sym);
                } else if (lexer.tokenType() == TokenType.DOLLAR) {
                    // 括弧を省略した関数呼び出し
                    getToken(); // skip "$"
                    ArrayList<Node> list = new ArrayList<>();
                    if (lexer.tokenType() == TokenType.SEMICOLON) {
                        node = new FuncallNode(lexer.getLocation(), method(sym), list);
                        break;
                    }
                    Node expr = new Expr(lexer, resolver).parse();
                    if (expr == null) throw new Exception(lexer.getLocation() + ": 文法エラーです");
                    list.add(expr);
                    while (lexer.tokenType() != TokenType.SEMICOLON) {
                        if (lexer.tokenType() != TokenType.COMMA) throw new Exception(lexer.getLocation() + ": 文法エラーです");
                        getToken();
                        list.add(new Expr(lexer, resolver).parse());
                    }
                    node = new FuncallNode(lexer.getLocation(), method(sym), list);
                } else {
                    node = method(sym);
                }
                break;
            case SEMICOLON:
                node = null;
                break;
            default:
                throw new Exception(lexer.getLocation() + ": 文法エラーです");
        }

        while (lexer.tokenType() == TokenType.LP) {
            ArrayList<Node> list = new ArrayList<>();
            getToken();
            if (lexer.tokenType() != TokenType.RP) list = new Args(lexer, resolver).arguments();
            if (lexer.tokenType() != TokenType.RP) throw new Exception(lexer.getLocation() + ": 文法エラー");
            getToken();
            node = new FuncallNode(lexer.getLocation(), node, list);
        }

        return node;
    }

    private Node method(Symbol sym) throws Exception {
        Node node;
        Handler core;
        switch (sym.getName()) {
            case "if":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern(0, "if"));
                break;
            case "print":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern(0, "print"));
                break;
            case "println":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern(0, "println"));
                break;
            case "exit":
                if (!Main.allowExitMethod()) throw new Exception(lexer.getLocation() + ": この実行環境で実行することはできません");
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern(0, "exit"));
                break;
            default:
                node = sym;
        }
        return node;
    }
}
