package net.zero918nobita.Xemime.ast;

/**
 * 二項演算子
 * @author Kodai Matsumoto
 */

public class ExprNode extends X_Code {
    /** 演算子の種類 */
    private TokenType op;
    /** 左側の式 */
    X_Code obj1;
    /** 右側の式 */
    X_Code obj2;

    public ExprNode(int n, TokenType operator, X_Code o1, X_Code o2) {
        super(n);
        op = operator;
        obj1 = o1;
        obj2 = o2;
    }

    public X_Code run() throws Exception {
        X_Code result = null;
        X_Code o1 = obj1.run();
        X_Code o2 = obj2.run();

        if (op == TokenType.AND) {
            result = o1.and(getLocation(), o2);
        } else if (op == TokenType.OR) {
            result = o1.or(getLocation(), o2);
        } else if (op == TokenType.XOR) {
            result = o1.xor(getLocation(), o2);
        } else {
            switch (op) { // 演算子ごとに処理を振り分ける
                case ADD:
                    result = o1.add(getLocation(), o2);
                    break;
                case SUB:
                    result = o1.sub(getLocation(), o2);
                    break;
                case MUL:
                    result = o1.multiply(getLocation(), o2);
                    break;
                case DIV:
                    result = o1.divide(getLocation(), o2);
                    break;
                case L:
                    result = o1.less(getLocation(), o2);
                    break;
                case LE:
                    result = o1.le(getLocation(), o2);
                    break;
                case G:
                    result = o1.greater(getLocation(), o2);
                    break;
                case GE:
                    result = o1.ge(getLocation(), o2);
                    break;
                case EQ:
                    if (obj1 instanceof X_Symbol && obj2 instanceof X_Symbol) {
                        X_Address a1 = ((X_Symbol)obj1).getAddress();
                        X_Address a2 = ((X_Symbol)obj2).getAddress();
                        result = a1.equals(a2) ? X_Bool.T : X_Bool.Nil;
                    } else {
                        result = X_Bool.Nil;
                    }
                    break;
                case EQL:
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
