package net.zero918nobita.xemime;

/** recognize メソッドで識別可能 */
public interface Recognizable {
    NodeType recognize();
    boolean is(NodeType comparison);
}
