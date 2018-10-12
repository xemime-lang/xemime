package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.ast.*;
import org.jetbrains.annotations.NotNull;

public class Int extends Numeric implements Recognizable {
    public Int(int location, int num) {
        super(location);
        value = num;
    }

    public Int(int num) {
        this(0, num);
    }

    @Override
    @NotNull
    public NodeType recognize() {
        return NodeType.INT;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Int) {
            return ((Int) obj).getValue().intValue() == this.value.intValue();
        } else {
            return (obj instanceof Double) && ((Double) obj).getValue().doubleValue() == this.value.doubleValue();
        }
    }

    @Override
    @NotNull
    public Numeric add(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Int(0, value.intValue() + i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() + dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line,  106);
        }
    }

    @Override
    @NotNull
    public Numeric sub(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Int(0, value.intValue() - i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() - dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 107);
        }
    }

    @Override
    @NotNull
    public Numeric multiply(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Int(0, value.intValue() * i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() * dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 108);
        }
    }

    @Override
    @NotNull
    public Numeric divide(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Int(0, value.intValue() / i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.intValue() / dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 109);
        }
    }

    @Override
    @NotNull
    public Numeric mod(int line, @NotNull Node rhs) throws FatalException {
        if (rhs.getClass() == Int.class) {
            Int i = (Int) rhs;
            return new Int(0, value.intValue() % i.getValue().intValue());
        } else {
            throw new FatalException(line, 141);
        }
    }

    @Override
    @NotNull
    public Bool less(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().intValue() < ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().intValue() < ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 110);
    }

    @Override
    @NotNull
    public Bool le(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().intValue() <= ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().intValue() <= ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 111);
    }

    @Override
    @NotNull
    public Bool greater(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().intValue() > ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().intValue() > ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 112);
    }

    @Override
    @NotNull
    public Bool ge(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().intValue() >= ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().intValue() >= ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 113);
    }
}
