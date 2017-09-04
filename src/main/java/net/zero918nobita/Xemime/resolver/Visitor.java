package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.AssignNode;

public interface Visitor {
    public void visit();
    public void visit(AssignNode node);
}
