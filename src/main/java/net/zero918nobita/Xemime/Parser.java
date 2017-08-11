package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * 再帰下降型の構文解析器です。
 * @author Kodai Matsumoto
 */

class Parser {
    /** 字句解析器 */
    private Lexer lex;

    /** 解析中のシンボルの種類 */
    private TokenType tokenType;

    /** 次のトークンをレキサを介して取得し、その種類を記録します。 */
    private void getToken() {
        if (lex.advance()) {
            tokenType = lex.tokenType();
        } else {
            tokenType = TokenType.EOS;
        }
    }

    /**
     * 構文解析を開始します。
     * @param lexer 構文解析器
     * @return 評価結果
     */
    X_Code parse(Lexer lexer) {
        X_Code obj = null;
        lex = lexer;
        getToken();
        try {
            obj = statement();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * ステートメントの構文解析を行います。
     * @return ステートメントの評価結果
     * @throws Exception ステートメントの記述がセミコロンで終了していない場合に例外を発生させます。
     */
    private X_Code statement() throws Exception {
        X_Code obj = expr();
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
     * @return 式の評価結果 ( 演算子を含む場合は、演算可能な X_BinExpr インスタンスを返します )
     * @throws Exception 式に不正な要素が含まれている場合に例外を発生させます。
     */
    private X_Code expr() throws Exception {
        X_Code obj = simpleExpr();
        switch (tokenType) {
            case L:
            case G:
            case EQ:
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
    private X_Code expr2(X_Code obj) throws Exception {
        X_BinExpr result = null;
        while ((tokenType == TokenType.L) ||
                (tokenType == TokenType.G) ||
                (tokenType == TokenType.EQ) ||
                (tokenType == TokenType.NE) ||
                (tokenType == TokenType.LE) ||
                (tokenType == TokenType.GE)) {
            TokenType op = tokenType;
            getToken();
            X_Code obj2 = simpleExpr();
            if (result == null) result = new X_BinExpr(op, obj, obj2);
            else result = new X_BinExpr(op, result, obj2);
        }
        return result;
    }

    /**
     * 単純式 ( 項同士が +, -, || で繋がれた式 ) の構文解析を行います。
     * @return 単純式の評価結果 ( 演算子を含む場合は、演算可能な X_BinExpr インスタンスを返します )
     * @throws Exception 単純式に不正な要素が含まれている場合に例外を発生させます。
     */
    private X_Code simpleExpr() throws Exception {
        X_Code obj = term();
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
    private X_Code simpleExpr2(X_Code obj) throws Exception {
        X_BinExpr result = null;
        while ((tokenType == TokenType.ADD) ||
                (tokenType == TokenType.SUB) ||
                (tokenType == TokenType.OR)) {
            TokenType op = tokenType;
            getToken();
            X_Code obj2 = term();
            if (result == null) {
                result = new X_BinExpr(op, obj, obj2);
            } else {
                result = new X_BinExpr(op, result, obj2);
            }
        }
        return result;
    }

    /**
     * 項 ( 因子同士が *, /, && で繋がれた要素 ) の構文解析を行います。
     * @return 項の評価結果 ( 演算子を含む場合は、演算可能な X_BinExpr インスタンスを返します )
     * @throws Exception 項に不正な要素が含まれている場合に例外を発生させます。
     */
    private X_Code term() throws Exception {
        X_Code obj = factor();
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
    private X_Code term2(X_Code obj) throws Exception {
        X_BinExpr result = null;
        while ((tokenType == TokenType.MUL) ||
                (tokenType == TokenType.DIV) ||
                (tokenType == TokenType.AND) ||
                (tokenType == TokenType.XOR)) {
            TokenType op = tokenType;
            getToken();
            X_Code obj2 = term();
            if (result == null) {
                result = new X_BinExpr(op, obj, obj2);
            } else {
                result = new X_BinExpr(op, result, obj2);
            }
        }
        return result;
    }

    /**
     * 因子 ( 文字列、真偽値、符号反転、論理否定、関数式、メッセージ式 ) の構文解析を行います。
     * @return 因子の評価結果 ( 演算子を含む場合は、演算可能な X_BinExpr インスタンスを返します )
     * @throws Exception 因子に不正な要素が含まれている場合 (ここではどの種類の因子にも該当しない場合、または閉じられていない括弧がある場合) に例外を発生させます。
     */
    private X_Code factor() throws Exception {
        X_Code obj;
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
                obj = new X_Not(factor());
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
                ArrayList<X_Code> list = new ArrayList<>();
                getToken();
                if (tokenType != TokenType.RP) list = args();
                if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
                getToken();
                obj = new X_DotCall(obj, sym, list);
            } else if (tokenType == TokenType.DECLARE) {
                getToken();
                X_Code c = expr();
                obj = new X_DotDeclare(obj, sym, c);
            } else if (tokenType == TokenType.ASSIGN) {
                getToken();
                X_Code c = expr();
                obj = new X_DotAssign(obj, sym, c);
            } else {
                obj = new X_DotExpr(obj, sym);
            }
        }
        return obj;
    }

    /**
     * 一次子 ( 数値、括弧で包まれた式、シンボル、宣言式、代入式、関数呼び出し、ブロック ) の構文解析を行います。
     * @return 一次子の評価結果
     * @throws Exception
     */
    private X_Code first() throws Exception {
        X_Code obj = null;
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
                obj = new X_Minus(first());
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
            case SYMBOL:
                X_Symbol sym = (X_Symbol)lex.value();
                getToken();
                if (tokenType == TokenType.DECLARE) {
                    // 変数宣言
                    getToken();
                    obj = new X_Declare(sym, expr());
                } else if (tokenType == TokenType.ASSIGN) {
                    // 宣言済みの変数への代入
                    getToken();
                    obj = new X_Assign(sym, expr());
                } else if (tokenType == TokenType.LP) {
                    // 関数呼び出し
                    obj = methodCall(sym);
                } else {
                    obj = methodOfCoreObject(sym);
                }
                break;
            default:
                throw new Exception("文法エラーです");
        }
        return obj;
    }

    private X_Code methodOfCoreObject(X_Symbol symbol) throws Exception {
        X_Code c;
        X_Handler core;
        switch (symbol.getName()){
            case "if":
                core = (X_Handler) Main.getValueOfSymbol(new X_Symbol("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(new X_Symbol("if"));
                break;
            case "print":
                core = (X_Handler) Main.getValueOfSymbol(new X_Symbol("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(new X_Symbol("print"));
                break;
            case "println":
                core = (X_Handler) Main.getValueOfSymbol(new X_Symbol("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(new X_Symbol("println"));
                break;
            case "exit":
                core = (X_Handler) Main.getValueOfSymbol(new X_Symbol("Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(new X_Symbol("exit"));
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
    private X_Code block() throws Exception {
        ArrayList<X_Code> list = null;
        getToken();
        while (tokenType != TokenType.RB) {
            X_Code o = expr();
            if (tokenType == TokenType.SEMICOLON) {
                if (list == null) list = new ArrayList<>();
                list.add(o);
            } else {
                throw new Exception("文法エラーです");
            }
            getToken();
        }
        getToken();
        return new X_Block(list);
    }

    /**
     * 関数呼び出しの構文解析を行います。
     * @param sym 関数名
     * @return 関数呼び出しの評価結果 ( 演算可能な X_Funcall インスタンスを返します )
     * @throws Exception 関数呼び出し部分で不正な要素が含まれている場合に例外を発生させます。
     */
    private X_Code methodCall(X_Symbol sym) throws Exception {
        ArrayList<X_Code> list = new ArrayList<>();
        getToken();
        if (tokenType != TokenType.RP) list = args();
        if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
        getToken();
        return new X_Funcall(methodOfCoreObject(sym), list);
    }

    /**
     * 引数リストの構文解析を行います。
     * @return 評価済みの引数リスト
     * @throws Exception 引数リスト中に不正な要素が含まれている場合に例外を発生させます。
     */
    private ArrayList<X_Code> args() throws Exception {
        ArrayList<X_Code> list = null;
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
     * @return 関数式の評価結果 ( 演算可能な X_Lambda インスタンスを返します )
     * @throws Exception 関数式中に不正な要素が含まれている場合 ( ここでは正しく括弧が閉じられていない場合 ) に例外を発生させます。
     */
    private X_Code lambda() throws Exception {
        ArrayList<X_Code> list = new ArrayList<>();
        getToken();
        if (tokenType != TokenType.LP) throw new Exception("文法エラーです");
        getToken();
        if (tokenType != TokenType.RP) list = symbols();
        if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
        getToken();
        return new X_Lambda(list, factor());
    }

    /**
     * 仮引数リストの構文解析を行います。
     * @return 仮引数リスト
     * @throws Exception シンボル以外の要素が列挙されている、または正しく要素が区切られていない場合に例外を発生させます。
     */
    private ArrayList<X_Code> symbols() throws Exception {
        ArrayList<X_Code> list = null;
        if (tokenType != TokenType.LP) {
            list = new ArrayList<>();
            list.add(expr());
            while (tokenType != TokenType.RP) {
                if (tokenType != TokenType.COMMA) throw new Exception("文法エラーです");
                getToken();
                if (tokenType != TokenType.SYMBOL) throw new Exception("文法エラーです");
                list.add(lex.value());
                getToken();
            }
        }
        return list;
    }
}
