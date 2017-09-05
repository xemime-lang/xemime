package net.zero918nobita.Xemime.ast;

import java.util.ArrayList;

/**
 * 整数オブジェクト
 * @author Kodai Matsumoto
 */

public class X_Int extends X_Numeric {
    public X_Int(int n, int num) {
        super(n);
        setMember(X_Symbol.intern(0, "abs"), new X_Abs());
        setMember(X_Symbol.intern(0, "to_s"), new X_ToS());
        setMember(X_Symbol.intern(0, "next"), new X_Succ());
        setMember(X_Symbol.intern(0, "succ"), new X_Succ());
        setMember(X_Symbol.intern(0, "pred"), new X_Pred());
        setMember(X_Symbol.intern(0, "times"), new X_Times());
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

    public int getValue() {
        return value.intValue();
    }

    X_Double to_d() {
        return new X_Double(0, this.getValue());
    }

    @Override
    public X_Numeric add(int line, Node obj) throws Exception {
        X_Numeric result;
        if (obj.getClass() == X_Int.class) {
            X_Int i = (X_Int)obj;
            result = new X_Int(0, value.intValue() + i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.intValue() + dbl.getValue());
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
            result = new X_Int(0, value.intValue() - i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.intValue() - dbl.getValue());
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
            result = new X_Int(0, value.intValue() * i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.intValue() * dbl.getValue());
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
            result = new X_Int(0, value.intValue() / i.getValue());
            return result;
        } else if (obj.getClass() == X_Double.class) {
            X_Double dbl = (X_Double)obj;
            result = new X_Double(0, value.intValue() / dbl.getValue());
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

    private class X_Abs extends X_Native {
        X_Abs() {
            super(0, 0);
        }
        protected X_Int exec(ArrayList<Node> params, X_Address self) throws Exception {
            return new X_Int(0, Math.abs(((X_Int)params.get(0)).getValue()));
        }
    }

    private class X_ToS extends X_Native {
        X_ToS() {
            super(0, 0);
        }

        @Override
        protected X_String exec(ArrayList<Node> params, X_Address self) throws Exception {
            return new X_String(0, params.get(0).toString());
        }
    }

    private class X_Succ extends X_Native {
        X_Succ() {
            super(0, 0);
        }

        @Override
        protected X_Int exec(ArrayList<Node> params, X_Address self) throws Exception {
            return new X_Int(0, ((X_Int)params.get(0)).getValue() + 1);
        }
    }

    private class X_Pred extends X_Native {
        X_Pred() {
            super(0, 0);
        }

        @Override
        protected X_Int exec(ArrayList<Node> params, X_Address self) throws Exception {
            return new X_Int(0, ((X_Int)params.get(0)).getValue() - 1);
        }
    }

    private class X_Times extends X_Native {
        X_Times() {
            super(0, 1);
        }

        @Override
        protected Node exec(ArrayList<Node> params, X_Address self) throws Exception {
            Node c = params.get(1).run();
            if (c instanceof X_Closure) {
                X_Closure f = (X_Closure)c;
                ArrayList<Node> list = new ArrayList<Node>() {{ add(f); }};
                for (int i = 0; i < ((X_Int)params.get(0)).getValue(); i++) {
                    c = f.exec(list, null);
                }
            } else {
                throw new Exception(params.get(1).getLocation() + ": 無名関数を指定してください");
            }
            return c;
        }
    }
}
