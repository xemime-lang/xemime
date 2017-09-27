package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.resolver.Type;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 一次子の構文解析を行います。
 * @author Kodai Matsumoto
 */

class First extends ParseUnit {
    First(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        Node node = null;
        switch (lexer.tokenType()) {
            case EOS:
                break;

            case UNIT:
                node = new Unit(lexer.getLocation(), null);
                getToken();
                break;

            case INT:
            case DOUBLE:
                node = lexer.value();
                getToken(); // skip int literal
                break;

            case ADD:
                getToken(); // skip "+"
                node = new First(lexer, resolver).parse();
                break;

            case SUB:
                getToken(); // skip "-"
                node = new MinusNode(lexer.getLocation(), new First(lexer, resolver).parse());
                break;

            case INCREMENT:
                getToken();

                // Syntax Error - [value] はシンボルではないので、前置インクリメント演算子を付与することはできません。
                if (lexer.tokenType() != TokenType.SYMBOL) throw new SyntaxError(lexer.getLocation(), 38, "`" + lexer.value() + "` はシンボルではないので、前置インクリメント演算子を付与することはできません。");

                node = new PrefixIncrementNode(lexer.getLocation(), (Symbol) lexer.value());
                getToken();
                break;

            case DECREMENT:
                getToken();

                // Syntax Error - [value] はシンボルではないので、後置インクリメント演算子を付与することはできません。
                if (lexer.tokenType() != TokenType.SYMBOL) throw new SyntaxError(lexer.getLocation(), 39, "`" + lexer.value() + "` はシンボルではないので、前置デクリメント演算子を付与することはできません");

                node = new PrefixDecrementNode(lexer.getLocation(), (Symbol) lexer.value());
                getToken();
                break;

            case LP:
                getToken(); // skip "("
                node = new LogicalExpr(lexer, resolver).parse();

                // Syntax Error - 対応する括弧がありません。
                if (lexer.tokenType() != TokenType.RP) throw new SyntaxError(lexer.getLocation(), 8, "対応する括弧がありません。");

                getToken(); // skip ")"
                break;

            case LB:
                node = new Block(lexer, resolver).parse();
                break;

            case DECLARE: {
                getToken(); // skip "let"

                // Syntax Error - 変数宣言式が不正です。宣言する変数の名称を記述してください。
                if (lexer.tokenType() != TokenType.SYMBOL)
                    throw new SyntaxError(lexer.getLocation(), 46, "変数宣言式が不正です。宣言する変数の名称を記述してください。");
                Symbol sym = (Symbol) lexer.value();
                getToken(); // skip symbol

                // Syntax Error - 変数宣言式が不正です。代入演算子を使用してください。
                if (lexer.tokenType() != TokenType.ASSIGN)
                    throw new SyntaxError(lexer.getLocation(), 47, "変数宣言式が不正です。代入演算子を使用してください。");

                getToken(); // skip "="
                Node value = new LogicalExpr(lexer, resolver).parse();

                // 現在のスコープに変数を登録する
                resolver.declareVar(sym, value);

                node = new DeclarationNode(lexer.getLocation(), sym, value);
                break;
            }

            case ATTR:
                getToken(); // skip "attr"

                // Syntax Error - `attr` の後ろに属性名を記述してください。
                if (lexer.tokenType() != TokenType.SYMBOL) throw new SyntaxError(lexer.getLocation(), 16, "`attr` の後ろに属性名を記述してください。");

                Symbol attr = (Symbol) lexer.value();
                getToken(); // skip symbol

                // Syntax Error - シンボルの後ろに波括弧 `{` を記述してください。
                if (lexer.tokenType() != TokenType.LB) throw new SyntaxError(lexer.getLocation(), 17, "シンボルの後ろに波括弧 `{` を記述してください。");

                HashMap<Symbol, Node> member = new HashMap<>();
                getToken(); // skip "{"

                Symbol name = (Symbol) lexer.value();
                getToken(); // skip name

                // Syntax Error - メンバ名と値の区切りのコロン `:` が必要です。
                if (lexer.tokenType() != TokenType.COLON) throw new SyntaxError(lexer.getLocation(), 18, "メンバ名と値の区切りのコロン `:` が必要です。");

                getToken(); // skip colon
                Node value = new LogicalExpr(lexer, resolver).parse();
                member.put(name, value);

                while (lexer.tokenType() != TokenType.RB) {

                    // Syntax Error - 区切りのカンマ `,` が必要です。
                    if (lexer.tokenType() != TokenType.COMMA) throw new SyntaxError(lexer.getLocation(), 19, "区切りのカンマ `,` が必要です。");

                    getToken(); // skip comma
                    name = (Symbol) lexer.value();
                    getToken(); // skip name

                    // Syntax Error - メンバ名と値の区切りのコロン `:` が必要です。
                    if (lexer.tokenType() != TokenType.COLON) throw new SyntaxError(lexer.getLocation(), 20, "メンバ名と値の区切りのコロン `:` が必要です。");

                    getToken(); // skip colon
                    value = new LogicalExpr(lexer, resolver).parse();
                    member.put(name, value);
                }

                getToken(); // skip "}"
                node = new AttrDeclarationNode(lexer.getLocation(), attr, member);
                resolver.declareVar(attr, node);
                break;

            case SUBST: {
                getToken(); // skip "subst"
                if (lexer.tokenType() != TokenType.SYMBOL) throw new Exception(lexer.getLocation() + ": 実体宣言式が不正です。");
                Symbol sym = (Symbol) lexer.value();
                getToken(); // skip symbol
                if (lexer.tokenType() == TokenType.ATTACH) {
                    getToken(); // skip "<-"
                    resolver.declareVar(Type.ANY, sym);
                    node = new SubstanceDeclarationNode(lexer.getLocation(), sym, new LogicalExpr(lexer, resolver).parse());
                } else {
                    throw new Exception(lexer.getLocation() + ": 実体宣言式が不正です。");
                }
                break;
            }

            case SYMBOL: {
                Symbol sym = (Symbol) lexer.value();
                // 変数の参照を解決する
                resolver.referVar(lexer.getLocation(), sym);
                getToken(); // skip symbol
                if (lexer.tokenType() == TokenType.ASSIGN) {
                    // 宣言済みの変数への代入
                    getToken();
                    node = new AssignNode(lexer.getLocation(), sym, new LogicalExpr(lexer, resolver).parse());
                } else if (lexer.tokenType() == TokenType.INCREMENT) {
                    getToken();
                    node = new SuffixIncrementNode(lexer.getLocation(), sym);
                } else if (lexer.tokenType() == TokenType.DECREMENT) {
                    getToken();
                    node = new SuffixDecrementNode(lexer.getLocation(), sym);
                } else {
                    node = method(sym);
                }
                break;
            }

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
