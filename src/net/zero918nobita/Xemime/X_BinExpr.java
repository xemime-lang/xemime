package net.zero918nobita.Xemime;

/**
 * 二項演算子
 * @author Kodai Matsumoto
 */

class X_BinExpr extends X_Object {
    private TokenType op; // 演算子の種類(＋,－,＊,／)
    X_Object obj1; // 左側の式
    X_Object obj2; // 右側の式

    X_BinExpr(TokenType operator, X_Object o1, X_Object o2) {
        op = operator;
        obj1 = o1;
        obj2 = o2;
    }

    X_Object run() throws Exception {
        X_Object result = null;
        X_Object o1 = obj1.run();
        X_Object o2 = obj2.run();

        if (op == TokenType.AND) {
            result = o1.and(o2);
        } else if (op == TokenType.OR) {
            result = o1.or(o2);
        } else if (op == TokenType.XOR) {
            result = o1.xor(o2);
        } else {
            switch (op) { // 演算子ごとに処理を振り分ける
                case ADD:
                    result = o1.add(o2);
                    break;
                case SUB:
                    result = o1.sub(o2);
                    break;
                case MUL:
                    result = o1.multiply(o2);
                    break;
                case DIV:
                    result = o1.divide(o2);
                    break;
                case L:
                    result = o1.less(o2);
                    break;
                case LE:
                    result = o1.le(o2);
                    break;
                case G:
                    result = o1.greater(o2);
                    break;
                case GE:
                    result = o1.ge(o2);
                    break;
                case EQ:
                    if (o1.equals(o2)) result = X_Bool.T;
                    else result = X_Bool.Nil;
                    break;
                case NE:
                    if (o1.equals(o2)) result = X_Bool.Nil;
                    else result = X_Bool.T;
                    break;
            }
        }
        return result;
    }
}
