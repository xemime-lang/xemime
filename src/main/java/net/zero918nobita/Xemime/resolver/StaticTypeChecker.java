package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Str;

class StaticTypeChecker {
    Type check(Resolver resolver, Node node) throws Exception {
        if (node instanceof Bool) {
            return check(resolver, (Bool) node);
        }else if (node instanceof Int) {
            return check(resolver, (Int) node);
        } else if (node instanceof Double) {
            return check(resolver, (Double) node);
        } else if (node instanceof Str) {
            return check(resolver, (Str) node);
        } else if (node instanceof Symbol) {
            return check(resolver, (Symbol) node);
        } else if (node instanceof ExprNode) {
            return check(resolver, (ExprNode) node);
        } else {
            throw new Exception("型を特定できませんでした。");
        }
    }

    Type check(Resolver resolver, Bool bool) {
        return Type.BOOL;
    }

    Type check(Resolver resolver, Int num) {
        return Type.INT;
    }

    Type check(Resolver resolver, Double num) {
        return  Type.DOUBLE;
    }

    Type check(Resolver resolver, Str str) {
        return Type.STRING;
    }

    Type check(Resolver resolver, Symbol symbol) throws SemanticError {
        return resolver.getTypeOfVariable(symbol);
    }

    Type check(Resolver resolver, ExprNode exprNode) throws Exception {
        Type lhs = check(resolver, exprNode.getLhs());
        Type rhs = check(resolver, exprNode.getRhs());
        switch (exprNode.getOperator()) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                if (lhs == Type.INT) {
                    if (rhs == Type.INT) {
                        return Type.INT;
                    } else if (rhs == Type.DOUBLE) {
                        return Type.DOUBLE;
                    } else {
                        throw new Exception("型を特定できませんでした。");
                    }
                } else if (lhs == Type.DOUBLE) {
                    if (rhs == Type.INT || rhs == Type.DOUBLE) {
                        return Type.DOUBLE;
                    } else {
                        throw new Exception("型を特定できませんでした。");
                    }
                } else {
                    throw new Exception("型を特定できませんでした。");
                }
            default:
                throw new Exception("型を特定できませんでした。");
        }
    }
}
