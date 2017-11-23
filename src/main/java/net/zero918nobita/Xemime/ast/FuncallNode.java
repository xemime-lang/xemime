package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.entity.Func;
import net.zero918nobita.Xemime.entity.Native;
import net.zero918nobita.Xemime.interpreter.Main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 関数呼び出しを表すノードです。
 * @author Kodai Matsumoto
 */

public class FuncallNode extends Node implements Recognizable {
    private Node func;
    private LinkedHashMap<Symbol, Node> map;
    private ArrayList<Node> arrayList;
    private boolean includesMySelf = false;

    public FuncallNode(int location, Node node, LinkedHashMap<Symbol, Node> map) throws Exception {
        super(location);
        if (node instanceof Symbol ||
                node instanceof Native ||
                node instanceof LambdaExprNode ||
                node instanceof ExprNode ||
                node instanceof DotAssignNode ||
                node instanceof DotExprNode ||
                node instanceof DotCallNode) {
            func = node;
        } else {
            // Fatal Exception - 関数呼び出しに失敗しました。
            throw new FatalException(getLocation(), 9);
        }
        this.map = map;
    }

    public FuncallNode(int location, Node node, ArrayList<Node> arrayList) throws Exception {
        super(location);
        if (node instanceof Symbol ||
                node instanceof Native ||
                node instanceof LambdaExprNode ||
                node instanceof ExprNode ||
                node instanceof DotAssignNode ||
                node instanceof DotExprNode ||
                node instanceof DotCallNode) {
            func = node;
        } else {
            // Fatal Exception - 関数呼び出しに失敗しました。
            throw new FatalException(getLocation(), 88);
        }
        this.arrayList = arrayList;
    }

    @Override
    public String toString() {
        return "Funcall(" + func + ", " + ((map == null) ? arrayList : map) + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FuncallNode)) return false;
        FuncallNode funcallNode = (FuncallNode) object;

        if (!func.equals(funcallNode.func)) return false;

        if (funcallNode.arrayList == null && funcallNode.map != null && arrayList != null)
            return (map.equals(funcallNode.map));
        else
            return funcallNode.arrayList != null && funcallNode.map == null &&
                    map != null && arrayList.equals(funcallNode.arrayList);
    }

    @Override
    public NodeType recognize() {
        return NodeType.FUNCALL;
    }

    public Node getFunc() {
        return func;
    }

    @Override
    public Node run() throws Exception {
        if (map != null) {
            if (func instanceof Native) {
                LinkedHashMap<Symbol, Node> params = new LinkedHashMap<>();
                for (Map.Entry<Symbol, Node> entry : map.entrySet()) params.put(entry.getKey(), entry.getValue().run());
                if (!includesMySelf) {
                    params.put(Symbol.intern("this"), func);
                    includesMySelf = true;
                }
                return ((Native) func).call(getLocation(), params, null);
            } else {
                if (func instanceof Symbol) {
                    Symbol symbol = (Symbol) func;
                    Node c = Main.getValueOfSymbol(symbol);
                    // Fatal Exception - 指定された関数は存在しません。
                    if (c == null) throw new FatalException(getLocation(), 12);
                    // Fatal Exception - 呼び出し対象が関数ではありません。
                    if (!(c instanceof Func)) throw new FatalException(getLocation(), 13);
                    func = c;
                } else {
                    func = func.run();
                }
                LinkedHashMap<Symbol, Node> params = new LinkedHashMap<>();
                for (Map.Entry<Symbol, Node> entry : map.entrySet()) params.put(entry.getKey(), entry.getValue().run());
                if (!includesMySelf) {
                    params.put(Symbol.intern("this"), func);
                    includesMySelf = true;
                }
                return ((Func)func).call(getLocation(), params, null);
            }
        } else {
            if (func instanceof Native) {
                if (!includesMySelf) {
                    arrayList.add(0, func);
                    includesMySelf = true;
                }
                return ((Native) func).call(getLocation(), arrayList, null);
            } else {
                if (func instanceof Symbol) {
                    Symbol symbol = (Symbol) func;
                    Node c = Main.getValueOfSymbol(symbol);
                    // Fatal Exception - 指定された関数は存在しません
                    if (c == null) throw new FatalException(getLocation(), 89);
                    // Fatal Exception - 呼び出し対象が関数ではありません
                    if (!(c instanceof Func)) throw new FatalException(getLocation(), 90);
                    func = c;
                } else {
                    func = func.run();
                }
                if (!includesMySelf) {
                    arrayList.add(0, func);
                    includesMySelf = true;
                }
                return ((Func)func).call(getLocation(), arrayList, null);
            }
        }
    }
}
