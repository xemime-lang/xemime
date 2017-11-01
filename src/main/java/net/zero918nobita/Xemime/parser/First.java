package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.resolver.TypeError;
import net.zero918nobita.Xemime.type.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

/**
 * 一次子の構文解析器
 * @author Kodai Matsumoto
 */

class First extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    First(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    /**
     * 一次子の構文解析と意味解析を行います。
     * @return 生成された AST
     */
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
                if (!(resolver.getTypeOfNode(node) instanceof IntType) &&
                        !(resolver.getTypeOfNode(node) instanceof DoubleType)) throw new TypeError(node.getLocation(), 62, "");
                break;

            case SUB:
                getToken(); // skip "-"
                node = new MinusNode(lexer.getLocation(), new First(lexer, resolver).parse());
                resolver.getTypeOfNode(node);
                break;

            case INCREMENT:
                node = prefixIncrement();
                break;

            case DECREMENT:
                node = prefixDecrement();
                break;

            case LP:
                node = bracket();
                break;

            case LB:
                node = new Block(lexer, resolver).parse();
                break;

            case DECLARE:
                node = declare();
                break;

            case ATTR:
                getToken(); // skip "attr"

                // Syntax Error - `attr` の後ろに属性名を記述してください。
                if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 16, "`attr` の後ろに属性名を記述してください。");

                Symbol attr = (Symbol) lexer.value();
                getToken(); // skip symbol

                // Syntax Error - シンボルの後ろに波括弧 `{` を記述してください。
                if (!current(LB)) throw new SyntaxError(lexer.getLocation(), 17, "シンボルの後ろに波括弧 `{` を記述してください。");

                HashMap<Symbol, Node> member = new HashMap<>();
                getToken(); // skip "{"

                Symbol name = (Symbol) lexer.value();
                getToken(); // skip name

                // Syntax Error - メンバ名と値の区切りのコロン `:` が必要です。
                if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 18, "メンバ名と値の区切りのコロン `:` が必要です。");

                getToken(); // skip colon
                Node value = new LogicalExpr(lexer, resolver).parse();
                member.put(name, value);

                while (!current(RB)) {

                    // Syntax Error - 区切りのカンマ `,` が必要です。
                    if (!current(COMMA)) throw new SyntaxError(lexer.getLocation(), 19, "区切りのカンマ `,` が必要です。");

                    getToken(); // skip comma
                    name = (Symbol) lexer.value();
                    getToken(); // skip name

                    // Syntax Error - メンバ名と値の区切りのコロン `:` が必要です。
                    if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 20, "メンバ名と値の区切りのコロン `:` が必要です。");

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
                if (lexer.tokenType() != SYMBOL) throw new Exception(lexer.getLocation() + ": 実体宣言式が不正です。");
                Symbol sym = (Symbol) lexer.value();
                getToken(); // skip symbol
                if (current(ATTACH)) {
                    getToken(); // skip "<-"
                    resolver.declareVar(new AnyType(), sym);
                    node = new SubstanceDeclarationNode(lexer.getLocation(), sym, new LogicalExpr(lexer, resolver).parse());
                } else {
                    throw new Exception(lexer.getLocation() + ": 実体宣言式が不正です。");
                }
                break;
            }

            case SYMBOL:
                node = symbol();
                break;

            case SEMICOLON:
                node = null;
                break;

            default:
                throw new SyntaxError(lexer.getLocation(), 72, "不明なトークン `" + lexer.value() + "` が検出されました。");
        }

        while (current(LP)) {
            LinkedHashMap<Symbol, Node> list = new LinkedHashMap<>();
            getToken();
            if (!current(RP)) list = new Args(lexer, resolver).arguments();
            if (!current(RP)) throw new SyntaxError(lexer.getLocation(), 71, "引数リストが正しく ( ) 括弧で括られていません。");
            getToken();
            node = new FuncallNode(lexer.getLocation(), node, list);
        }

        return node;
    }

    /**
     * 前置インクリメントから始まる文法要素の構文解析を行います。
     * @return AST
     */
    private Node prefixIncrement() throws Exception {
        int line = lexer.getLocation();
        getToken();

        // Syntax Error - [value] はシンボルではないので、前置インクリメント演算子を付与することはできません。
        if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 38, "`" + lexer.value() + "` はシンボルではないので、前置インクリメント演算子を付与することはできません。");

        Symbol symbol = (Symbol) lexer.value();

        // Type Error - [value] は整数型変数ではないので、前置インクリメント演算子を付与することはできません。
        if (!(resolver.getTypeOfVariable(symbol) instanceof IntType)) throw new TypeError(line, 53, "`" + symbol + "` は整数型変数ではないので、前置インクリメント演算子を付与することはできません。");

        getToken();
        return new PrefixIncrementNode(lexer.getLocation(), symbol);
    }

    /**
     * 前置デクリメントから始まる文法要素の構文解析を行います、
     * @return AST
     */
    private Node prefixDecrement() throws Exception {
        int line = lexer.getLocation();
        getToken();

        // Syntax Error - [value] はシンボルではないので、前置デクリメント演算子を付与することはできません。
        if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 39, "`" + lexer.value() + "` はシンボルではないので、前置デクリメント演算子を付与することはできません");

        Symbol symbol = (Symbol) lexer.value();

        // Type Error - [value] は整数型変数ではないので、前置デクリメント演算を付与することはできません。
        if (!(resolver.getTypeOfVariable(symbol) instanceof IntType)) throw new TypeError(line, 55, "`" + symbol + "` は整数型変数ではないので、前置デクリメント演算子を付与することはできません。");

        getToken();
        return new PrefixDecrementNode(lexer.getLocation(), symbol);
    }

    private Node bracket() throws Exception {
        getToken(); // skip "("
        Node node = new LogicalExpr(lexer, resolver).parse();

        // Syntax Error - 対応する括弧がありません。
        if (!current(RP)) throw new SyntaxError(lexer.getLocation(), 8, "対応する括弧がありません。");

        getToken(); // skip ")"
        return node;
    }

    /**
     * 変数宣言式の構文解析を行います。
     * @return AST
     */
    private Node declare() throws Exception {
        // 型推論がデフォルトで有効になります。
        boolean inference = true;
        getToken(); // skip `let`

        // Syntax Error - 変数宣言式が不正です。宣言する変数の名称を記述してください。
        if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 46, "変数宣言式が不正です。宣言する変数の名称を記述してください。");

        Symbol sym = (Symbol) lexer.value();
        getToken(); // skip symbol

        if (current(COLON)) {
            inference = false;
            getToken(); // skip `:`
            if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 50, "コロン `:` の後ろでデータ型を指定してください。");
            getToken();
            Symbol type_name = (Symbol) lexer.value();
            if (type_name.equals(Symbol.intern("Int"))) {
                resolver.declareVar(new IntType(), sym);
            } else if (type_name.equals(Symbol.intern("Double"))) {
                resolver.declareVar(new DoubleType(), sym);
            } else if (type_name.equals(Symbol.intern("Bool"))) {
                resolver.declareVar(new BoolType(), sym);
            } else if (type_name.equals(Symbol.intern("String"))) {
                resolver.declareVar(new StrType(), sym);
            } else {
                throw new SyntaxError(type_name.getLocation(), 73, "`" + type_name + "` 型は定義されていません。");
            }
        }

        // Syntax Error - 変数宣言式が不正です。代入演算子を使用してください。
        if (!current(ASSIGN)) throw new SyntaxError(lexer.getLocation(), 47, "変数宣言式が不正です。代入演算子を使用してください。");

        // 代入演算子の位置
        int locationOfAssignmentOp = lexer.getLocation();

        getToken(); // skip `=`
        Node value = new LogicalExpr(lexer, resolver).parse();

        if (inference) {
            // 現在のスコープに変数を登録する
            resolver.declareVar(sym, value);
        } else {
            resolver.assignVar(locationOfAssignmentOp, sym, value);
        }

        return new DeclarationNode(lexer.getLocation(), sym, value);
    }

    /**
     * シンボルから始まる文法要素の構文解析を行います。
     * @return AST
     */
    private Node symbol() throws Exception {
        Node node;
        Symbol sym = (Symbol) lexer.value();
        // 変数の参照を解決する
        resolver.referVar(lexer.getLocation(), sym);
        getToken(); // skip symbol
        if (current(ASSIGN)) {
            // 宣言済みの変数への代入
            int locationOfAssignmentOp = lexer.getLocation();
            getToken();
            Node assignedValue = new LogicalExpr(lexer, resolver).parse();
            resolver.assignVar(locationOfAssignmentOp, sym, assignedValue);
            node = new AssignNode(lexer.getLocation(), sym, assignedValue);
        } else if (current(INCREMENT)) {
            int line = lexer.getLocation();
            getToken();
            if (!(resolver.getTypeOfVariable(sym) instanceof IntType)) throw new TypeError(line, 52, "`" + sym + "` は整数型ではないので、後置インクリメント演算子を付与することはできません。");
            node = new SuffixIncrementNode(lexer.getLocation(), sym);
        } else if (current(DECREMENT)) {
            int line = lexer.getLocation();
            getToken();
            if (!(resolver.getTypeOfVariable(sym) instanceof IntType)) throw new TypeError(line, 53, "`" + sym + "` は整数型ではないので、後置デクリメント演算子を付与することはできません。");
            node = new SuffixDecrementNode(lexer.getLocation(), sym);
        } else {
            node = method(sym);
        }
        return node;
    }

    /**
     * 組み込みメソッドの読み込みを行います。<br>
     * 渡されたシンボルが組み込みメソッドの名称と一致していれば、
     * 対応する組み込みメソッドへの参照で値を上書きします。
     * @param sym 対象のシンボル
     * @return AST
     */
    private Node method(Symbol sym) throws Exception {
        Node node;
        Handler core;
        switch (sym.getName()) {
            case "print":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern("print"));
                break;
            case "println":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern("println"));
                break;
            case "exit":
                if (!Main.allowExitMethod()) throw new Exception(lexer.getLocation() + ": この実行環境で実行することはできません");
                core = (Handler) Main.getValueOfSymbol(Symbol.intern("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                node = core.message(lexer.getLocation(), Symbol.intern("exit"));
                break;
            default:
                node = sym;
        }
        return node;
    }
}
