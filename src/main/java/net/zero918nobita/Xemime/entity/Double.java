package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.ast.Node;

/**
 * 小数値
 * @author Kodai Matsumoto
 */

public class Double extends Numeric {

    public Double(int location, double num) {
        super(location);
        value = num;
    }

    public Double(double num) {
        this(0, num);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).getValue() == this.value.doubleValue();
        } else {
            return (obj instanceof Int) && ((Int) obj).getValue() == this.value.doubleValue();
        }
    }

    public double getValue() {
        return value.doubleValue();
    }

    /**
     * この小数値を左辺、渡されたノードを右辺として加算を行います。
     * @param line 演算を行う行の行番号
     * @param rhs 右辺
     * @return 和
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Numeric add(int line, Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() + i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double) rhs;
            result = new Double(0, value.doubleValue() + dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 120);
        }
    }

    /**
     * この小数値を左辺、渡されたノードを右辺として減算を行います。
     * @param line 演算を行う行の行番号
     * @param rhs 右辺
     * @return 差
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Numeric sub(int line, Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() - i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.doubleValue() - dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 121);
        }
    }

    /**
     * この小数値を左辺、渡されたノードを右辺として乗算を行います。
     * @param line 演算を行う行の行番号
     * @param rhs 右辺
     * @return 積
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Numeric multiply(int line, Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() * i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.doubleValue() * dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 122);
        }
    }

    /**
     * この小数値を左辺、渡されたノードを右辺として除算を行います。
     * @param line 演算を行う行の行番号
     * @param rhs 右辺
     * @return 商
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Numeric divide(int line, Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() / i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.doubleValue() / dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 123);
        }
    }

    /**
     * 大小を比較して、この小数値が比較対象「より小さい」場合に真値を返します。
     * @param line 演算を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool less(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() < ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() < ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 124);
    }

    /**
     * 大小を比較して、この小数値が比較対象「以下」の場合に真値を返します。
     * @param line 演算を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool le(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() <= ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() <= ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 125);
    }

    /**
     * 大小を比較して、この小数値が比較対象「より大きい」場合に真値を返します。
     * @param line 演算を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool greater(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() > ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() > ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 126);
    }

    /**
     * 大小を比較して、この小数値が比較対象「以上」の場合に真値を返します。
     * @param line 演算を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool ge(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() >= ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() >= ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 127);
    }
}
