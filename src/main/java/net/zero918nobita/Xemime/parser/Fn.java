package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.entity.Unit;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.type.Type;

import java.util.TreeMap;

import static net.zero918nobita.Xemime.lexer.TokenType.LP;
import static net.zero918nobita.Xemime.lexer.TokenType.RP;
import static net.zero918nobita.Xemime.lexer.TokenType.SYMBOL;

class Fn extends ParseUnit {
    /**
     * @param lexer 字句解析器
     * @param resolver 意味解析器
     */
    Fn(Lexer lexer, Resolver resolver) {
        super(lexer, resolver);
    }

    @Override
    protected Node parse() throws Exception {
        getToken(); // skip `fn`

        // Syntax Error - `fn` の後ろに関数名を記述してください。
        if (!current(SYMBOL))
            throw new SyntaxError(lexer.getLocation(), 66, "`fn` の後ろに関数名を記述してください。");

        Symbol sym = (Symbol) lexer.value();
        getToken(); // skip symbol

        // Syntax Error - 関数名の後ろに ( ) 括弧で括られた引数リストを記述してください。
        if (!current(LP))
            throw new SyntaxError(lexer.getLocation(), 67, "関数名の後ろに ( ) 括弧で括られた引数リストを記述してください。");

        getToken(); // skip `(`
        TreeMap<Symbol, Type> args = new TreeMap<>();

        if (!current(RP)) {
            if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 70, "");
            Symbol name = (Symbol) lexer.value();
            getToken(); // skip symbol

        }
        return new Unit(0, null);
    }
}
