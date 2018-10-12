package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Bool;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Node implements Recognizable {
    protected int location;  // 行番号

    public Node(int location) {
        this.location = location;
    }

    @Override
    public NodeType recognize() {
        return NodeType.NODE;
    }

    @Override
    public boolean is(NodeType comparison) {
        return comparison == recognize();
    }

    public int getLocation() {
        return this.location;
    }

    public Node run() throws Exception {
        return this;
    }

    public Node add(int location, Node rhs) throws FatalException {
        // `+` 演算子は使用できません
        throw new FatalException(location,  98);
    }

    public Node add(Node rhs) throws FatalException {
        return add(0, rhs);
    }

    public Node sub(int location, Node rhs) throws FatalException {
        // `-` 演算子は使用できません
        throw new FatalException(location, 99);
    }

    public Node sub(Node rhs) throws FatalException {
        return sub(0, rhs);
    }

    public Node multiply(int location, Node rhs) throws FatalException {
        // `*` 演算子は使用できません
        throw new FatalException(location,  100);
    }

    public Node multiply(Node rhs) throws FatalException {
        return multiply(0, rhs);
    }

    public Node divide(int location, Node rhs) throws FatalException {
        // `/` 演算子は使用できません
        throw new FatalException(location, 101);
    }

    public Node divide(Node rhs) throws FatalException {
        return divide(0, rhs);
    }

    public Node mod(int location, Node rhs) throws FatalException {
        // `%` 演算子は使用できません
        throw new FatalException(location, 140);
    }

    public Bool less(int location, Node rhs) throws FatalException {
        // `<` 演算子は使用できません
        throw new FatalException(location, 102);
    }

    public Bool less(Node rhs) throws FatalException {
        return less(0, rhs);
    }

    public Bool le(int location, Node rhs) throws FatalException {
        // `<=` 演算子は使用できません
        throw new FatalException(location, 103);
    }

    public Bool le(Node rhs) throws FatalException {
        return le(0, rhs);
    }

    public Bool greater(int location, Node rhs) throws FatalException {
        // `>` 演算子は使用できません。
        throw new FatalException(location, 104);
    }

    public Bool greater(Node rhs) throws FatalException {
        return greater(0, rhs);
    }

    public Bool ge(int location, Node rhs) throws FatalException {
        // `>=` 演算子は使用できません。
        throw new FatalException(location, 105);
    }

    public Bool ge(Node rhs) throws FatalException {
        return ge(0, rhs);
    }

    public Bool and(int location, Node rhs) throws FatalException {
        // `&&` 演算子は使用できません。
        throw new FatalException(location, 114);
    }

    public Bool and(Node rhs) throws FatalException {
        return and(0, rhs);
    }

    public Bool or(int location, Node rhs) throws FatalException {
        // `||` 演算子は使用できません。
        throw new FatalException(location, 115);
    }

    public Bool or(Node rhs) throws FatalException {
        return or(0, rhs);
    }

    public Bool xor(int location, Node rhs) throws FatalException {
        // `^` 演算子は使用できません。
        throw new FatalException(location, 116);
    }

    public Bool xor(Node rhs) throws FatalException {
        return xor(0, rhs);
    }

    public Node message(int location, Symbol symbol) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にフィールドは設定できません");
    }

    public Node message(int location, Symbol symbol, LinkedHashMap<Symbol, Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドは設定できません");
    }

    public Node message(int location, Symbol symbol, ArrayList<Node> params) throws Exception {
        throw new Exception(location + ": `" + toString() + "` にメソッドを設定できません");
    }
}
