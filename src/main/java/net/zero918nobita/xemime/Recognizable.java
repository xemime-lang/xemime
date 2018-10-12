package net.zero918nobita.xemime;

/**
 * recognize メソッドで識別可能なノードに実装されるインターフェースです。
 * @author Kodai Matsumoto
 */

public interface Recognizable {
    NodeType recognize();
    boolean is(NodeType comparison);
}
