package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.*;

/**
 * Visitor パターン
 * @author Kodai Matsumoto
 */

public interface Visitor {
    void visit(DeclareNode node);

    void visit(AssignNode node);
    void visit(DotAssignNode node);

    void visit(ExprNode node);
    void visit(DotExprNode node);

    void visit(DotCallNode node);

    void visit(BlockNode node);

    void visit(LambdaExprNode node);
}
