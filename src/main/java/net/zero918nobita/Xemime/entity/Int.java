package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.ast.*;

import java.util.ArrayList;

/**
 * 整数オブジェクト
 * @author Kodai Matsumoto
 */

public class Int extends Numeric {
    public Int(int location, int num) {
        super(location);
        setMember(Symbol.intern(0, "abs"), new X_Abs());
        setMember(Symbol.intern(0, "to_s"), new X_ToS());
        setMember(Symbol.intern(0, "next"), new X_Succ());
        setMember(Symbol.intern(0, "succ"), new X_Succ());
        setMember(Symbol.intern(0, "pred"), new X_Pred());
        setMember(Symbol.intern(0, "times"), new X_Times());
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

    private class X_Abs extends Native {
        X_Abs() {
            super(0, 0);
        }
        protected Int exec(ArrayList<Node> params, Address self) throws Exception {
            return new Int(0, Math.abs(((Int)params.get(0)).getValue()));
        }
    }

    private class X_ToS extends Native {
        X_ToS() {
            super(0, 0);
        }

        @Override
        protected Str exec(ArrayList<Node> params, Address self) throws Exception {
            return new Str(0, params.get(0).toString());
        }
    }

    private class X_Succ extends Native {
        X_Succ() {
            super(0, 0);
        }

        @Override
        protected Int exec(ArrayList<Node> params, Address self) throws Exception {
            return new Int(0, ((Int)params.get(0)).getValue() + 1);
        }
    }

    private class X_Pred extends Native {
        X_Pred() {
            super(0, 0);
        }

        @Override
        protected Int exec(ArrayList<Node> params, Address self) throws Exception {
            return new Int(0, ((Int)params.get(0)).getValue() - 1);
        }
    }

    private class X_Times extends Native {
        X_Times() {
            super(0, 1);
        }

        @Override
        protected Node exec(ArrayList<Node> params, Address self) throws Exception {
            Node c = params.get(1).run();
            if (c instanceof Closure) {
                Closure f = (Closure)c;
                ArrayList<Node> list = new ArrayList<Node>() {{ add(f); }};
                for (int i = 0; i < ((Int)params.get(0)).getValue(); i++) {
                    c = f.exec(list, null);
                }
            } else {
                throw new Exception(params.get(1).getLocation() + ": 無名関数を指定してください");
            }
            return c;
        }
    }
}
