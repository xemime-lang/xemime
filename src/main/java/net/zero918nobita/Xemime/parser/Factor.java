package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;

import java.util.ArrayList;

/**
 * 因子を構文解析します。
 * @author Kodai Matsumoto
 */

class Factor extends ParseUnit {
    Factor(Lexer lexer) {
        super(lexer);
    }

    @Override
    Node parse() throws Exception {
        Node node;
        switch (tokenType) {
            case STRING:
                node = lexer.value();
                getToken();
                break;
            case T:
                node = Bool.T;
                getToken();
                break;
            case NIL:
                node = Bool.Nil;
                getToken();
                break;
            case NOT:
                getToken();
                node = new NotNode(lexer.getLocation(), new Factor(lexer).parse());
                break;
            case LAMBDA:
                node = new Lambda(lexer).parse();
                break;
            default:
                node = new First(lexer).parse();
        }

        // メッセージ式
        while (tokenType == TokenType.PERIOD) {
            getToken();
            if (tokenType != TokenType.SYMBOL) throw new Exception("文法エラーです");
            Symbol sym = (Symbol)lexer.value();
            getToken();
            if (tokenType == TokenType.LP) {
                ArrayList<Node> list = new ArrayList<>();
                getToken();
                if (tokenType != TokenType.RP) list = new Args(lexer).parse();
                if (tokenType != TokenType.RP) throw new Exception("文法エラーです");
                getToken();
                node = new DotCallNode(lexer.getLocation(), node, sym, list);
            } else if (tokenType == TokenType.ASSIGN) {
                getToken();
                Node c = new Expr(lexer).parse();
                node = new DotAssignNode(lexer.getLocation(), node, sym, c);
            } else {
                node = new DotExprNode(lexer.getLocation(), node, sym);
            }
        }
        return node;
    }
}
