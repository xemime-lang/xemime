package net.zero918nobita.Xemime.ast;

abstract public class Node {
    String name;
    abstract Node getChild();
    abstract void addChild();
}
