package net.zero918nobita.Xemime;

/**
 * 構文解析器
 * @author Kodai Matsumoto
 */

class Parser {
    private Lexer lex;
    private TokenType tokenType;
    private int token;

    private void getToken() {
        if (lex.advance()) {
            tokenType = lex.tokenType();
            token = lex.token();
        } else {
            tokenType = TokenType.EOS;
            token = -1;
        }
    }

    X_Object parse(Lexer lexer) {
        X_Object obj = null;
        lex = lexer;
        getToken();
        try {
            obj = statement();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private X_Object statement() throws Exception {
        X_Object obj = expr();
        if (obj != null) {
            switch (token) {
                case ';':
                    break;
                default:
                    throw new Exception("文法エラーです");
            }
        }
        return obj;
    }

    private X_Object expr() throws Exception {
        X_Object obj = term();
        switch (token) {
            case '+':
            case '-':
                obj = expr2(obj);
                break;
        }
        return obj;
    }

    private X_Object expr2(X_Object obj) throws Exception {
        X_BinExpr result = null;
        while ((token == '+') || (token == '-')) {
            int op = token;
            getToken();
            X_Object obj2 = term();
            if (result == null) result = new X_BinExpr(op, obj, obj2);
            else result = new X_BinExpr(op, result, obj2);
        }
        return result;
    }

    private X_Object term() throws Exception {
        X_Object obj = factor();
        switch (token) {
            case '*':
            case '/':
                obj = term2(obj);
                break;
        }
        return obj;
    }

    private X_Object term2(X_Object obj) throws Exception {
        X_BinExpr result = null;
        while ((token == '*') || (token == '/')) {
            int op = token;
            getToken();
            X_Object obj2 = term();
            if (result == null) {
                result = new X_BinExpr(op, obj, obj2);
            } else {
                result = new X_BinExpr(op, result, obj2);
            }
        }
        return result;
    }

    private X_Object factor() throws Exception {
        X_Object obj = null;
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
            case SYMBOL:
                X_Symbol sym = (X_Symbol)lex.value();
                getToken();
                if (token == '=') {
                    getToken();
                    obj = new X_Assign(sym, expr());
                } else {
                    obj = sym;
                }
                break;
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
            default:
                switch (token) {
                    case '-':
                        getToken();
                        obj = new X_Minus(factor());
                        break;
                    case '(':
                        getToken();
                        obj = expr();
                        if (token != ')') throw new Exception("文法エラー: 対応する括弧がありません");
                        getToken();
                        break;
                    default:
                        throw new Exception("文法エラーです");
                }
        }
        return obj;
    }
}
