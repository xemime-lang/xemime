package net.zero918nobita.Xemime.entity;

import net.zero918nobita.Xemime.NodeType;
import net.zero918nobita.Xemime.Recognizable;
import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.ast.Node;

/**
 * 文字列
 * @author Kodai Matsumoto
 */

public class Str extends Node implements Recognizable {
    private final String value;

    public Str(int location, String str) {
        super(location);
        value = str;
    }

    public Str(String str) {
        this(0, str);
    }

    @Override
    public NodeType recognize() {
        return NodeType.STR;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Str) && (this.value.equals(obj.toString()));
    }

    /**
     * 文字列を結合します。
     * @param line 文字列の結合を行う行の行番号
     * @param obj 結合する文字列
     * @return 結合後の文字列
     * @throws FatalException 渡されたノードが評価されて文字列にならなかった場合に例外を発生させます。
     */
    @Override
    public Str add(int line, Node obj) throws FatalException {
        if (obj instanceof Str) {
            return new Str(0, this.value + ((Str) obj).value);
        } else {
            throw new FatalException(line, 128);
        }
    }
}
