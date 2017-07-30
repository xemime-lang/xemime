package net.zero918nobita.Xemime;

/**
 * 構文解析器
 * @author Kodai Matsumoto
 */

class Parser {
    private Lexer lex;
    private TokenType tokenType;

    private void getToken() {
        if (lex.advance()) {
            tokenType = lex.tokenType();
        } else {
            tokenType = TokenType.EOS;
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
            switch (tokenType) {
                case SEMICOLON:
                    break;
                default:
                    System.out.println(tokenType);
                    throw new Exception("文法エラーです");
            }
        }
        return obj;
    }

    private X_Object expr() throws Exception {
        X_Object obj = simpleExpr();
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

    private X_Object expr2(X_Object obj) throws Exception {
        X_BinExpr result = null;
        while ((tokenType == TokenType.L) ||
                (tokenType == TokenType.G) ||
                (tokenType == TokenType.EQ) ||
                (tokenType == TokenType.NE) ||
                (tokenType == TokenType.LE) ||
                (tokenType == TokenType.GE)) {
            TokenType op = tokenType;
            getToken();
            X_Object obj2 = simpleExpr();
            if (result == null) result = new X_BinExpr(op, obj, obj2);
            else result = new X_BinExpr(op, result, obj2);
        }
        return result;
    }

    private X_Object simpleExpr() throws Exception {
        X_Object obj = term();
        switch (tokenType) {
            case ADD:
            case SUB:
            case OR:
                obj = simpleExpr2(obj);
        }
        return obj;
    }

    private X_Object simpleExpr2(X_Object obj) throws Exception {
        X_BinExpr result = null;
        while ((tokenType == TokenType.ADD) ||
                (tokenType == TokenType.SUB) ||
                (tokenType == TokenType.OR)) {
            TokenType op = tokenType;
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

    private X_Object term() throws Exception {
        X_Object obj = factor();
        switch (tokenType) {
            case MUL:
            case DIV:
            case AND:
                obj = term2(obj);
                break;
        }
        return obj;
    }

    private X_Object term2(X_Object obj) throws Exception {
        X_BinExpr result = null;
        while ((tokenType == TokenType.MUL) ||
                (tokenType == TokenType.DIV) ||
                (tokenType == TokenType.AND)) {
            TokenType op = tokenType;
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
                if (tokenType == TokenType.ASSIGN) {
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
            case SUB:
                getToken();
                obj = new X_Minus(factor());
                break;
            case LP:
                getToken();
                obj = expr();
                if (tokenType != TokenType.RP) throw new Exception("文法エラー: 対応する括弧がありません");
                getToken();
                break;
            case NOT:
                getToken();
                obj = new X_Not(factor());
                break;
            default:
                throw new Exception("文法エラーです");
        }
        return obj;
    }
}
