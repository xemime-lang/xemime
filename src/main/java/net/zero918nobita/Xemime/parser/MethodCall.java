package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.FuncallNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;

import java.util.ArrayList;

/**
 * メソッド呼び出し部分を構文解析します。
 * @author Kodai Matsumoto
 */

class MethodCall extends ParseUnit {
    MethodCall(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    Node parse() throws Exception {
        throw new FatalError(lexer.getLocation(), 6);
    }

    Node methodCall(Symbol sym) throws Exception {
        ArrayList<Node> list = new ArrayList<>();
        getToken();
        if (lexer.tokenType() != TokenType.RP) list = new Args(lexer, resolver).arguments();
        if (lexer.tokenType() != TokenType.RP) throw new SyntaxError(lexer.getLocation(), 7, "関数呼び出しの閉じ括弧がありません。");
        getToken();
        return new FuncallNode(lexer.getLocation(), methodOfCoreObject(sym), list);
    }

    private Node methodOfCoreObject(Symbol sym) throws Exception {
        Node c;
        Handler core;
        switch (sym.getName()){
            case "if":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(lexer.getLocation(), Symbol.intern(0, "if"));
                break;
            case "print":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(lexer.getLocation(), Symbol.intern(0, "print"));
                break;
            case "println":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(lexer.getLocation(), Symbol.intern(0, "println"));
                break;
            case "exit":
                core = (Handler) Main.getValueOfSymbol(Symbol.intern(0, "Core"));
                if (core == null) throw new Exception("深刻なエラー: Core オブジェクトがありません");
                c = core.message(lexer.getLocation(), Symbol.intern(0, "exit"));
                break;
            default:
                c = sym;
        }
        return c;
    }
}
