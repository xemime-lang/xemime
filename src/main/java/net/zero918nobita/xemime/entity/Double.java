package net.zero918nobita.xemime.entity;

import net.zero918nobita.xemime.NodeType;
import net.zero918nobita.xemime.Recognizable;
import net.zero918nobita.xemime.ast.FatalException;
import net.zero918nobita.xemime.ast.Node;
import org.jetbrains.annotations.NotNull;

public class Double extends Numeric implements Recognizable {

    public Double(int location, double num) {
        super(location);
        value = num;
    }

    public Double(double num) {
        this(0, num);
    }

    @Override
    @NotNull
    public NodeType recognize() {
        return NodeType.DOUBLE;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).getValue().doubleValue() == this.value.doubleValue();
        } else {
            return (obj instanceof Int) && ((Int) obj).getValue().intValue() == this.value.doubleValue();
        }
    }

    @Override
    @NotNull
    public Numeric add(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() + i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double) rhs;
            result = new Double(0, value.doubleValue() + dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 120);
        }
    }

    @Override
    @NotNull
    public Numeric sub(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() - i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.doubleValue() - dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 121);
        }
    }

    @Override
    @NotNull
    public Numeric multiply(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() * i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.doubleValue() * dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 122);
        }
    }

    @Override
    @NotNull
    public Numeric divide(int line, @NotNull Node rhs) throws FatalException {
        Numeric result;
        if (rhs.getClass() == Int.class) {
            Int i = (Int)rhs;
            result = new Double(0, value.doubleValue() / i.getValue().intValue());
            return result;
        } else if (rhs.getClass() == Double.class) {
            Double dbl = (Double)rhs;
            result = new Double(0, value.doubleValue() / dbl.getValue().doubleValue());
            return result;
        } else {
            throw new FatalException(line, 123);
        }
    }

    @Override
    @NotNull
    public Bool less(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().doubleValue() < ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().doubleValue() < ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 124);
    }

    @Override
    @NotNull
    public Bool le(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().doubleValue() <= ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().doubleValue() <= ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 125);
    }

    @Override
    @NotNull
    public Bool greater(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().doubleValue() > ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().doubleValue() > ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 126);
    }

    @Override
    @NotNull
    public Bool ge(int line, @NotNull Node rhs) throws FatalException {
        if (rhs instanceof Int) return (this.getValue().doubleValue() >= ((Int) rhs).getValue().intValue()) ? Bool.getT() : Bool.getNil();
        else if (rhs instanceof Double) return (this.getValue().doubleValue() >= ((Double) rhs).getValue().doubleValue()) ? Bool.getT() : Bool.getNil();
        else throw new FatalException(line, 127);
    }
}
