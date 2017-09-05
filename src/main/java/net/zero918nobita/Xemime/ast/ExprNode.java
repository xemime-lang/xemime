package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.lexer.TokenType;

/**
 * 二項演算を表すノードです。<br>
 * parser パッケージの Parser.parse メソッドによってインスタンスが生成され、
 * 実行時には run メソッドで演算結果を返します。
 * @author Kodai Matsumoto
 */

public class ExprNode extends X_Code {
    /** 演算子の種類 */
    private TokenType op;
    /** 左辺 */
    X_Code lhs;
    /** 右辺 */
    X_Code rhs;

    /**
     * 二項演算を表すノードを生成します。
     * @param location 行番号
     * @param operator 演算子の種類
     * @param lhs 左辺
     * @param rhs 右辺
     */
    public ExprNode(int location, TokenType operator, X_Code lhs, X_Code rhs) {
        super(location);
        op = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * 二項演算を行い、得られた値を返します。
     * @return 演算結果
     * @throws Exception 左辺または右辺が不正である場合に例外を発生させます。
     */
    public X_Code run() throws Exception {
        // 二項演算の結果
        X_Code result = null;
        // 左辺の評価結果
        X_Code e_lhs = lhs.run();
        // 右辺の評価結果
        X_Code e_rhs = rhs.run();

        if (op == TokenType.AND) {
            result = e_lhs.and(getLocation(), e_rhs);
        } else if (op == TokenType.OR) {
            result = e_lhs.or(getLocation(), e_rhs);
        } else if (op == TokenType.XOR) {
            result = e_lhs.xor(getLocation(), e_rhs);
        } else {
            switch (op) { // 演算子ごとに処理を振り分ける
                case ADD:
                    result = e_lhs.add(getLocation(), e_rhs);
                    break;
                case SUB:
                    result = e_lhs.sub(getLocation(), e_rhs);
                    break;
                case MUL:
                    result = e_lhs.multiply(getLocation(), e_rhs);
                    break;
                case DIV:
                    result = e_lhs.divide(getLocation(), e_rhs);
                    break;
                case L:
                    result = e_lhs.less(getLocation(), e_rhs);
                    break;
                case LE:
                    result = e_lhs.le(getLocation(), e_rhs);
                    break;
                case G:
                    result = e_lhs.greater(getLocation(), e_rhs);
                    break;
                case GE:
                    result = e_lhs.ge(getLocation(), e_rhs);
                    break;
                case EQ:
                    if (lhs instanceof X_Symbol && rhs instanceof X_Symbol) {
                        X_Address a1 = ((X_Symbol)lhs).getAddress();
                        X_Address a2 = ((X_Symbol)rhs).getAddress();
                        result = a1.equals(a2) ? X_Bool.T : X_Bool.Nil;
                    } else {
                        result = X_Bool.Nil;
                    }
                    break;
                case EQL:
                    if (e_lhs.equals(e_rhs)) result = X_Bool.T;
                    else result = X_Bool.Nil;
                    break;
                case NE:
                    if (e_lhs.equals(e_rhs)) result = X_Bool.Nil;
                    else result = X_Bool.T;
                    break;
            }
        }
        return result;
    }
}
