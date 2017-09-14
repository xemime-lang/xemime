package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.*;

/**
 * 整数オブジェクト
 * @author Kodai Matsumoto
 */

public class Int extends Numeric {
    public Int(int location, int num) {
        super(location);
        value = num;
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

    Double to_d() {
        return new Double(0, this.getValue());
    }

    @Override
    public Numeric add(int line, Node obj) throws Exception {
        Numeric result;
        if (obj.getClass() == Int.class) {
            Int i = (Int)obj;
            result = new Int(0, value.intValue() + i.getValue());
            return result;
        } else if (obj.getClass() == Double.class) {
            Double dbl = (Double)obj;
            result = new Double(0, value.intValue() + dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを足そうとしました");
        }
    }

    @Override
    public Numeric sub(int line, Node obj) throws Exception {
        Numeric result;
        if (obj.getClass() == Int.class) {
            Int i = (Int)obj;
            result = new Int(0, value.intValue() - i.getValue());
            return result;
        } else if (obj.getClass() == Double.class) {
            Double dbl = (Double)obj;
            result = new Double(0, value.intValue() - dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを引こうとしました");
        }
    }

    @Override
    public Numeric multiply(int line, Node obj) throws Exception {
        Numeric result;
        if (obj.getClass() == Int.class) {
            Int i = (Int)obj;
            result = new Int(0, value.intValue() * i.getValue());
            return result;
        } else if (obj.getClass() == Double.class) {
            Double dbl = (Double)obj;
            result = new Double(0, value.intValue() * dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを掛けようとしました");
        }
    }

    @Override
    public Numeric divide(int line, Node obj) throws Exception {
        Numeric result;
        if (obj.getClass() == Int.class) {
            Int i = (Int)obj;
            result = new Int(0, value.intValue() / i.getValue());
            return result;
        } else if (obj.getClass() == Double.class) {
            Double dbl = (Double)obj;
            result = new Double(0, value.intValue() / dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを割ろうとしました");
        }
    }

    @Override
    public Bool less(int line, Node obj) throws Exception {
        if (obj instanceof Int) return (this.getValue() < ((Int) obj).getValue()) ? Bool.T : Bool.Nil;
        else if (obj instanceof Double) return (this.getValue() < ((Double) obj).getValue()) ? Bool.T : Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    public Bool le(int line, Node obj) throws Exception {
        if (obj instanceof Int) return (this.getValue() <= ((Int) obj).getValue()) ? Bool.T : Bool.Nil;
        else if (obj instanceof Double) return (this.getValue() <= ((Double) obj).getValue()) ? Bool.T : Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    public Bool greater(int line, Node obj) throws Exception {
        if (obj instanceof Int) return (this.getValue() > ((Int) obj).getValue()) ? Bool.T : Bool.Nil;
        else if (obj instanceof Double) return (this.getValue() > ((Double) obj).getValue()) ? Bool.T : Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    public Bool ge(int line, Node obj) throws Exception {
        if (obj instanceof Int) return (this.getValue() >= ((Int) obj).getValue()) ? Bool.T : Bool.Nil;
        else if (obj instanceof Double) return (this.getValue() >= ((Double) obj).getValue()) ? Bool.T : Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }
}
