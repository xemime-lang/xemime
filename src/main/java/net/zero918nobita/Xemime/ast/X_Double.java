package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * 小数オブジェクト
 * @author Kodai Matsumoto
 */

public class X_Double extends X_Numeric {

    public X_Double(int n, double num) {
        super(n);
        setMember(X_Symbol.intern(0, "abs"), new X_Abs());
        value = num;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof X_Double) {
            return ((X_Double) obj).getValue() == this.value.doubleValue();
        } else {
            return (obj instanceof X_Int) && ((X_Int) obj).getValue() == this.value.doubleValue();
        }
    }

    double getValue() {
        return value.doubleValue();
    }

    @Override
    public X_Numeric add(int line, Node obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(0, value.doubleValue() + i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double) obj;
            result = new X_Double(0, value.doubleValue() + dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを足そうとしました");
        }
    }

    @Override
    public X_Numeric sub(int line, Node obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(0, value.doubleValue() - i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.doubleValue() - dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを引こうとしました");
        }
    }

    @Override
    public X_Numeric multiply(int line, Node obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(0, value.doubleValue() * i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.doubleValue() * dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを掛けようとしました");
        }
    }

    @Override
    public X_Numeric divide(int line, Node obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(0, value.doubleValue() / i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.doubleValue() / dbl.getValue());
            return result;
        } else {
            throw new Exception(line + ": 数値以外のものを割ろうとしました");
        }
    }

    @Override
    public X_Bool less(int line, Node obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() < ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() < ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    public X_Bool le(int line, Node obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() <= ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() <= ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    public X_Bool greater(int line, Node obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() > ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() > ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    public X_Bool ge(int line, Node obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() >= ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() >= ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception(line + ": Int, Double 以外のデータ型と大小を比較することはできません");
    }

    class X_Abs extends X_Native {
        X_Abs() {
            super(0, 0);
        }
        protected X_Double exec(ArrayList<Node> params) throws Exception {
            return new X_Double(0, Math.abs(((X_Double)params.get(0)).getValue()));
        }
    }
}
