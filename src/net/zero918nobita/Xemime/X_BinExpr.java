package net.zero918nobita.Xemime;

/**
 * 二項演算子
 * @author Kodai Matsumoto
 */

class X_BinExpr extends X_Object {
    private int op; // 演算子の種類(＋,－,＊,／)
    X_Object obj1; // 左側の式
    X_Object obj2; // 右側の式

    X_BinExpr(int operator, X_Object o1, X_Object o2) {
        op = operator;
        obj1 = o1;
        obj2 = o2;
    }

    X_Object run(Environment env) throws Exception {
        X_Object o1 = obj1.run(env); // code1を計算する
        X_Object o2 = obj2.run(env); // code2を計算する
        X_Object result = null;
        switch (op) { // 演算子ごとに処理を振り分ける
            case '+':
                result = o1.add(o2);
                break;
            case '-':
                result = o1.sub(o2);
                break;
            case '*':
                result = o1.multiply(o2);
                break;
            case '/':
                result = o1.divide(o2);
                break;
        }
        return result;
    }
}
