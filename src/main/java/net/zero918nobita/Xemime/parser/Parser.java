package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * 再帰下降型の構文解析器です。
 * @author Kodai Matsumoto
 */

public class Parser {
    private int line = 1;

    /** 字句解析器 */
    private Lexer lex;

    /** 解析中のシンボルの種類 */
    private TokenType tokenType;

    /** 参照解決を担当する */
    private Resolver resolver;

    public Parser() {
        resolver = new Resolver();
        // 組み込みオブジェクトや省略表記のメソッドのシンボルを resolver に登録することで、
        // それらのシンボルに対する参照解決の失敗を防ぎます。
        resolver.declareVar(X_Symbol.intern(0, "Core"));
        resolver.declareVar(X_Symbol.intern(0, "if"));
        resolver.declareVar(X_Symbol.intern(0, "print"));
        resolver.declareVar(X_Symbol.intern(0, "println"));
        resolver.declareVar(X_Symbol.intern(0, "exit"));
    }

    /** 次のトークンをレキサを介して取得し、その種類を記録します。 */
    private void getToken() throws Exception {
        if (lex.advance()) {
            tokenType = lex.tokenType();
        } else {
            tokenType = TokenType.EOS;
        }
    }

    /**
     * 構文解析を開始します。
     * @param str ソースコード
     * @return 評価結果
     */
    public ArrayList<Node> parse(String str) throws Exception {
        ArrayList<Node> result = new ArrayList<>();
        lex = new Lexer(line, str.replaceAll("\r\n|\r", "\n"));
        getToken();
        Node code;
        while (tokenType != TokenType.EOS) {
            code = statement();
            if (code != null) result.add(code);
            getToken();
        }
        line = lex.getLocation();
        return result;
    }

    public void goDown(int n) {
        line += n;
    }

    public int getLocation() {
        return line;
    }

    /**
     * ステートメントの構文解析を行います。
     * @return ステートメントの評価結果
     * @throws Exception ステートメントの記述がセミコロンで終了していない場合に例外を発生させます。
     */
    private Node statement() throws Exception {
        Node obj = expr();
        if (obj != null) {
            switch (tokenType) {
                case SEMICOLON:
                    break;
                default:
                    throw new Exception("文法エラーです");
            }
        }
        return obj;
    }

    /**
     * 式 ( 単純式同士が ==, !=, &lt;, &lt;=, &gt;, &gt;= で繋がれた式 ) の構文解析を行います。
     * @return 式の評価結果 ( 演算子を含む場合は、演算可能な ExprNode インスタンスを返します )
     * @throws Exception 式に不正な要素が含まれている場合に例外を発生させます。
     */
    private Node expr() throws Exception {
        Node obj = simpleExpr();
        switch (tokenType) {
            case L:
            case G:
            case EQ:
            case EQL:
            case NE:
            case LE:
            case GE:
                obj = expr2(obj);
                break;
        }
        return obj;
    }


    /**
     * 演算子を含む式の右辺の構文解析を行います。
     * @param obj 式の右辺
     * @return 式の評価結果
     * @throws Exception 式の右辺に不正な要素が含まれている場合に例外を発生させます。
     */
    private Node expr2(Node obj) throws Exception {
        ExprNode result = null;
        while ((tokenType == TokenType.L) ||
                (tokenType == TokenType.G) ||
                (tokenType == TokenType.EQ) ||
                (tokenType == TokenType.EQL) ||
                (tokenType == TokenType.NE) ||
                (tokenType == TokenType.LE) ||
                (tokenType == TokenType.GE)) {
            TokenType op = tokenType;
            getToken();
            Node obj2 = simpleExpr();
            if (result == null) result = new ExprNode(lex.getLocation(), op, obj, obj2);
            else result = new ExprNode(lex.getLocation(), op, result, obj2);
        }
        return result;
    }

    /**
     * 単純式 ( 項同士が +, -, || で繋がれた式 ) の構文解析を行います。
     * @return 単純式の評価結果 ( 演算子を含む場合は、演算可能な ExprNode インスタンスを返します )
     * @throws Exception 単純式に不正な要素が含まれている場合に例外を発生させます。
     */
    private Node simpleExpr() throws Exception {
        Node obj = term();
        switch (tokenType) {
            case ADD:
            case SUB:
            case OR:
                obj = simpleExpr2(obj);
                break;
        }
        return obj;
    }

    /**
     * 演算子を含む単純式の右辺の構文解析を行います。
     * @param obj 単純式の右辺
     * @return 単純式の評価結果
     * @throws Exception 単純式の右辺に不正な要素が含まれている場合に例外を発生させます。
     */
    private Node simpleExpr2(Node obj) throws Exception {
        ExprNode result = null;
        while ((tokenType == TokenType.ADD) ||
                (tokenType == TokenType.SUB) ||
                (tokenType == TokenType.OR)) {
            TokenType op = tokenType;
            getToken();
            Node obj2 = term();
            if (result == null) {
                result = new ExprNode(lex.getLocation(), op, obj, obj2);
            } else {
                result = new ExprNode(lex.getLocation(), op, result, obj2);
            }
        }
        return result;
    }

    /**
     * 項 ( 因子同士が *, /, && で繋がれた要素 ) の構文解析を行います。
     * @return 項の評価結果 ( 演算子を含む場合は、演算可能な ExprNode インスタンスを返します )
     * @throws Exception 項に不正な要素が含まれている場合に例外を発生させます。
     */
    private Node term() throws Exception {
        Node obj = factor();
        switch (tokenType) {
            case MUL:
            case DIV:
            case AND:
            case XOR:
                obj = term2(obj);
                break;
        }
        return obj;
    }

    /**
     * 演算を含む項の構文解析を行います。
     * @param obj 項の左辺
     * @return 項の演算結果
     * @throws Exception 項の右辺に不正な要素が含まれている場合に例外を発生させます。
     */
    private Node term2(Node obj) throws Exception {
        ExprNode result = null;
        while ((tokenType == TokenType.MUL) ||
                (tokenType == TokenType.DIV) ||
                (tokenType == TokenType.AND) ||
                (tokenType == TokenType.XOR)) {
            TokenType op = tokenType;
            getToken();
            Node obj2 = term();
            if (result == null) {
                result = new ExprNode(lex.getLocation(), op, obj, obj2);
            } else {
                result = new ExprNode(lex.getLocation(), op, result, obj2);
            }
        }
        return result;
    }

    /**
     * 因子 ( 文字列、真偽値、符号反転、論理否定、関数式、メッセージ式 ) の構文解析を行います。
     * @return 因子の評価結果 ( 演算子を含む場合は、演算可能な ExprNode インスタンスを返します )
     * @throws Exception 因子に不正な要素が含まれている場合 (ここではどの種類の因子にも該当しない場合、または閉じられていない括弧がある場合) に例外を発生させます。
     */
    private Node factor() throws Exception {
        Node obj;
        switch (tokenType) {
            case STRING:
                obj = lex.value();
                getToken();
                break;
            case T:
                obj = X_Bool.T;
                getToken();
                break;
            case NIL:
                obj = X_Bool.Nil;
                getToken();
                break;
            case NOT:
                getToken();
                obj = new X_Not(lex.getLocation(), factor());
                break;
            case LAMBDA:
                obj = lambda();
                break;
            default:
                obj = first();
        }

        // メッセージ式
        while (tokenType == TokenType.PERIOD) {
            getToken();
            if (tokenType != TokenType.SYMBOL) throw new Exception("文法エラーです");
            X_Symbol sym = (X_Symbol)lex.value();
            getToken();
            if (tokenType == TokenType.LP) {
                ArrayList<Node> list = new ArrayList<>();
                getToken();
                if (tokenType != TokenType.RP) list = args();
                if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
                getToken();
                obj = new DotCallNode(lex.getLocation(), obj, sym, list);
            } else if (tokenType == TokenType.ASSIGN) {
                getToken();
                Node c = expr();
                obj = new DotAssignNode(lex.getLocation(), obj, sym, c);
            } else {
                obj = new DotExprNode(lex.getLocation(), obj, sym);
            }
        }
        return obj;
    }

    /**
     * 一次子 ( 数値、括弧で包まれた式、シンボル、宣言式、代入式、関数呼び出し、ブロック ) の構文解析を行います。
     * @return 一次子の評価結果
     * @throws Exception 一次子に不正な要素が含まれている場合
     */
    private Node first() throws Exception {
        Node obj = null;
        switch (tokenType) {
            case EOS:
                break;
            case INT:
                obj = lex.value();
                getToken();
                break;
            case DOUBLE:
                obj = lex.value();
                getToken();
                break;
            case SUB:
                getToken();
                obj = new X_Minus(lex.getLocation(), first());
                break;
            case LP:
                getToken();
                obj = expr();
                if (tokenType != TokenType.RP) throw new Exception("文法エラー: 対応する括弧がありません");
                getToken();
                break;
            case LB:
                obj = block();
                break;
            case DECLARE:
                getToken();
                if (tokenType == TokenType.SYMBOL) {
                    X_Symbol sym = (X_Symbol)lex.value();
                    getToken();
                    if (tokenType == TokenType.ASSIGN) {
                        getToken();
                        // 現在のスコープに変数を登録する
                        resolver.declareVar(sym);
                        obj = new DeclareNode(lex.getLocation(), sym, expr());
                    } else {
                        throw new Exception("宣言式が不正です");
                    }
                } else {
                    throw new Exception("宣言式が不正です");
                }
                break;
            case SYMBOL:
                X_Symbol sym = (X_Symbol)lex.value();
                // 変数の参照を解決する
                resolver.referVar(lex.getLocation(), sym);
                getToken();
                if (tokenType == TokenType.ASSIGN) {
                    // 宣言済みの変数への代入
                    getToken();
                    obj = new AssignNode(lex.getLocation(), sym, expr());
                } else if (tokenType == TokenType.LP) {
                    // 関数呼び出し
                    obj = methodCall(sym);
                } else {
                    obj = methodOfCoreObject(sym);
                }
                break;
            case SEMICOLON:
                obj = null;
                break;
            default:
                throw new Exception("文法エラーです");
        }

        while (tokenType == TokenType.LP) {
            ArrayList<Node> list = new ArrayList<>();
            getToken();
            if (tokenType != TokenType.RP) list = args();
            if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
            getToken();
            obj = new X_Funcall(getLocation(), obj, list);
        }

        return obj;
    }

    private Node methodOfCoreObject(X_Symbol symbol) throws Exception {
        Node c;
        X_Handler core;
        switch (symbol.getName()){
            case "if":
                core = (X_Handler) Main.getValueOfSymbol(X_Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(getLocation(), X_Symbol.intern(0, "if"));
                break;
            case "print":
                core = (X_Handler) Main.getValueOfSymbol(X_Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(getLocation(), X_Symbol.intern(0, "print"));
                break;
            case "println":
                core = (X_Handler) Main.getValueOfSymbol(X_Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(getLocation(), X_Symbol.intern(0, "println"));
                break;
            case "exit":
                core = (X_Handler) Main.getValueOfSymbol(X_Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(getLocation(), X_Symbol.intern(0, "exit"));
                break;
            default:
                c = symbol;
        }
        return c;
    }

    /**
     * ブロックの構文解析を行います。
     * @return ブロックの評価結果
     * @throws Exception ブロック中に不正な式が含まれている場合に例外を発生させます。
     */
    private Node block() throws Exception {
        ArrayList<Node> list = null;
        getToken();
        resolver.addScope();
        while (tokenType != TokenType.RB) {
            Node o = expr();
            if (tokenType == TokenType.SEMICOLON) {
                if (list == null) list = new ArrayList<>();
                list.add(o);
            } else {
                throw new Exception("文法エラーです");
            }
            getToken();
        }
        resolver.removeScope();
        getToken();
        return new BlockNode(lex.getLocation(), list);
    }

    /**
     * 関数呼び出しの構文解析を行います。
     * @param sym 関数名
     * @return 関数呼び出しの評価結果 ( 演算可能な X_Funcall インスタンスを返します )
     * @throws Exception 関数呼び出し部分で不正な要素が含まれている場合に例外を発生させます。
     */
    private Node methodCall(X_Symbol sym) throws Exception {
        ArrayList<Node> list = new ArrayList<>();
        getToken();
        if (tokenType != TokenType.RP) list = args();
        if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
        getToken();
        return new X_Funcall(lex.getLocation(), methodOfCoreObject(sym), list);
    }

    /**
     * 引数リストの構文解析を行います。
     * @return 評価済みの引数リスト
     * @throws Exception 引数リスト中に不正な要素が含まれている場合に例外を発生させます。
     */
    private ArrayList<Node> args() throws Exception {
        ArrayList<Node> list = null;
        if (tokenType != TokenType.RP) {
            list = new ArrayList<>();
            list.add(expr());
            while (tokenType != TokenType.RP) {
                if (tokenType != TokenType.COMMA) throw new Exception("文法エラーです");
                getToken();
                list.add(expr());
            }
        }
        return list;
    }

    /**
     * 関数式の構文解析を行います。
     * @return 関数式の評価結果 ( 演算可能な X_Closure インスタンスを返します )
     * @throws Exception 関数式中に不正な要素が含まれている場合 ( ここでは正しく括弧が閉じられていない場合 ) に例外を発生させます。
     */
    private Node lambda() throws Exception {
        ArrayList<X_Symbol> list = new ArrayList<>();
        getToken();
        if (tokenType == TokenType.SYMBOL) {
            list.add((X_Symbol)lex.value());
            getToken();
            while (tokenType != TokenType.ARROW) {
                if (tokenType != TokenType.COMMA) throw new Exception(getLocation() + ": ラムダ式の仮引数が不正です");
                getToken();
                if (tokenType != TokenType.SYMBOL) throw new Exception(getLocation() + ": ラムダ式の仮引数が不正です");
                list.add((X_Symbol)lex.value());
                getToken();
            }
        } else if (tokenType == TokenType.LP) {
            getToken();
            if (tokenType == TokenType.SYMBOL) {
                list.add((X_Symbol)lex.value());
                getToken();
                while (tokenType != TokenType.RP) {
                    if (tokenType != TokenType.COMMA) throw new Exception(getLocation() + ": ラムダ式の仮引数が不正です");
                    getToken();
                    if (tokenType != TokenType.SYMBOL) throw new Exception(getLocation() + ": ラムダ式の仮引数が不正です");
                    list.add((X_Symbol)lex.value());
                    getToken();
                }
            }
            getToken();
            if (tokenType != TokenType.ARROW) throw new Exception(getLocation() + ": ラムダ式のアロー演算子がありません");
        } else if (tokenType != TokenType.ARROW) {
            throw new Exception(getLocation() + ": ラムダ式の仮引数リストまたはアロー演算子が必要です");
        }
        getToken();
        resolver.addScope();
        for (X_Symbol sym : list) resolver.declareVar(sym);
        Node expr = expr();
        resolver.removeScope();
        return new LambdaExprNode(getLocation(), list, expr);
    }
}
