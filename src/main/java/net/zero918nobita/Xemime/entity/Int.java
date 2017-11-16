package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.*;

/**
 * 整数値
 * @author Kodai Matsumoto
 */

public class Int extends Numeric {
    public Int(int location, int num) {
        super(location);
        value = num;
    }

    public Int(int num) {
        this(0, num);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Int) {
            return ((Int) obj).getValue() == this.value.intValue();
        } else {
            return (obj instanceof Double) && ((Double) obj).getValue() == this.value.doubleValue();
        }
    }

    public int getValue() {
        return value.intValue();
    }

    /**
     * この整数値を左辺、渡されたノードを右辺として加算を行います。
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
            result = new Int(0, value.intValue() + i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() + dbl.getValue());
            return result;
        } else {
            throw new FatalException(line,  106);
        }
    }

    /**
     * この整数値を左辺、渡されたノードを右辺として減算を行います。
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
            result = new Int(0, value.intValue() - i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() - dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 107);
        }
    }

    /**
     * この整数を左辺、渡されたノードを右辺として乗算を行います。
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
            result = new Int(0, value.intValue() * i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() * dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 108);
        }
    }

    /**
     * この整数値を左辺、渡されたノードを右辺として除算が行います。
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
            result = new Int(0, value.intValue() / i.getValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() / dbl.getValue());
            return result;
        } else {
            throw new FatalException(line, 109);
        }
    }

    /**
     * 大小を比較して、この整数値が比較対象「より小さい」場合に真値を返します。
     * @param line 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool less(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() < ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() < ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 110);
    }

    /**
     * 大小を比較して、この整数値が比較対象「以上」の場合に真値を返します。
     * @param line 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool le(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() <= ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() <= ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 111);
    }

    /**
     * 大小を比較して、この整数値が比較対象「より大きい」場合に真値を返します。
     * @param line 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool greater(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() > ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() > ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 112);
    }

    /**
     * 大小を比較して、この整数値が比較対象「以上」の場合に真値を返します。
     * @param line 比較を行う行の行番号
     * @param rhs 比較対象
     * @return 真偽値
     * @throws FatalException 右辺が数値ではない場合に例外を発生させます。
     */
    @Override
    public Bool ge(int line, Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue() >= ((Int) rhs).getValue()) ? Bool.T : Bool.Nil;
        else if (rhs instanceof Double) return (this.getValue() >= ((Double) rhs).getValue()) ? Bool.T : Bool.Nil;
        else throw new FatalException(line, 113);
    }
}
