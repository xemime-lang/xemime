package net.zero918nobita.Xemime;

import java.util.ArrayList;

/**
 * 小数オブジェクト
 * @author Kodai Matsumoto
 */

class X_Double extends X_Numeric {

    X_Double(double num) {
        super();
        setMember(new X_Symbol("abs"), new X_Abs());
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
    X_Numeric add(X_Code obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(value.doubleValue() + i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double) obj;
            result = new X_Double(value.doubleValue() + dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを足そうとしました");
        }
    }

    @Override
    X_Numeric sub(X_Code obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(value.doubleValue() - i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.doubleValue() - dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを引こうとしました");
        }
    }

    @Override
    X_Numeric multiply(X_Code obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(value.doubleValue() * i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.doubleValue() * dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを掛けようとしました");
        }
    }

    @Override
    X_Numeric divide(X_Code obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Double(value.doubleValue() / i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.doubleValue() / dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを割ろうとしました");
        }
    }

    @Override
    X_Bool less(X_Code obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() < ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() < ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    X_Bool le(X_Code obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() <= ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() <= ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    X_Bool greater(X_Code obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() > ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() > ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    X_Bool ge(X_Code obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() >= ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() >= ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    class X_Abs extends X_Native {
        X_Abs() {
            super(0);
        }
        protected X_Double exec(ArrayList<X_Code> params) throws Exception {
            return new X_Double(Math.abs(((X_Double)params.get(0)).getValue()));
        }
    }
}
