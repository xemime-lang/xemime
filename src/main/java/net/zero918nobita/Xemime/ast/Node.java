package net.zero918nobita.Xemime.ast;

/**
 * ノードの原型
 * @author Kodai Matsumoto
 */

abstract public class Node {
    String name;
    abstract Node getChild();
    abstract void addChild();
}
