package net.zero918nobita.Xemime;

/**
 * 整数オブジェクト
 * @author Kodai Matsumoto
 */

class X_Int extends X_Numeric {
    X_Int(int num) {
        super();
        value = num;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof X_Int) {
            return ((X_Int) obj).getValue() == this.value.intValue();
        } else {
            return (obj instanceof X_Double) && ((X_Double) obj).getValue() == this.value.doubleValue();
        }
    }

    int getValue() {
        return value.intValue();
    }

    X_Double to_d() {
        return new X_Double(this.getValue());
    }
    
    @Override
    X_Numeric add(X_Object obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Int(value.intValue() + i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.intValue() + dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを足そうとしました");
        }
    }

    @Override
    X_Numeric sub(X_Object obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Int(value.intValue() - i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.intValue() - dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを引こうとしました");
        }
    }

    @Override
    X_Numeric multiply(X_Object obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Int(value.intValue() * i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.intValue() * dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを掛けようとしました");
        }
    }

    @Override
    X_Numeric divide(X_Object obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Int(value.intValue() / i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(value.intValue() / dbl.getValue());
            return result;
        } else {
            throw new Exception("数値以外のものを割ろうとしました");
        }
    }

    @Override
    X_Bool less(X_Object obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() < ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() < ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    X_Bool le(X_Object obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() <= ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() <= ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    X_Bool greater(X_Object obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() > ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() > ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }

    @Override
    X_Bool ge(X_Object obj) throws Exception {
        if (obj instanceof X_Int) return (this.getValue() >= ((X_Int) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else if (obj instanceof X_Double) return (this.getValue() >= ((X_Double) obj).getValue()) ? X_Bool.T : X_Bool.Nil;
        else throw new Exception("Int, Double 以外のデータ型と大小を比較することはできません");
    }
}
