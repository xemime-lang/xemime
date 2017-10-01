package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.FunctionNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.type.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

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

        Symbol func_name = (Symbol) lexer.value();
        getToken(); // skip symbol

        // Syntax Error - 関数名の後ろに ( ) 括弧で括られた引数リストを記述してください。
        if (!current(LP))
            throw new SyntaxError(lexer.getLocation(), 67, "関数名の後ろに ( ) 括弧で括られた引数リストを記述してください。");

        getToken(); // skip `(`
        skipLineBreaks();
        TreeMap<Symbol, Type> args = new TreeMap<>();

        if (!current(RP)) {
            if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 70, "");
            Symbol name = (Symbol) lexer.value();
            getToken(); // skip symbol
            if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 74, "");
            getToken(); // skip `:`
            if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 75, "");
            Type type = convertSymbolIntoType((Symbol) lexer.value());
            getToken(); // skip symbol
            skipLineBreaks();
            args.put(name, type);
            if (!current(RP)) while (!current(RP)) {
                if (!current(COMMA)) throw new SyntaxError(lexer.getLocation(), 77, "");
                getToken(); // skip `,`
                skipLineBreaks();
                if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 70, "");
                name = (Symbol) lexer.value();
                getToken(); // skip symbol
                if (!current(COLON)) throw new SyntaxError(lexer.getLocation(), 74, "");
                getToken(); // skip `:`
                if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 75, "");
                type = convertSymbolIntoType((Symbol) lexer.value());
                args.put(name, type);
                getToken();
                skipLineBreaks();
            }
        }

        for (Map.Entry<Symbol, Type> entry : args.entrySet()) resolver.declareVar(entry.getValue(), entry.getKey());

        getToken(); // skip `}`
        if (!current(ARROW)) throw new SyntaxError(lexer.getLocation(), 78, "");
        getToken(); // skip `->`
        if (!current(SYMBOL)) throw new SyntaxError(lexer.getLocation(), 79, "");
        Type return_type = convertSymbolIntoType((Symbol) lexer.value());
        getToken(); // skip symbol

        resolver.declareVar(new FuncType(return_type, args), func_name);

        if (!current(LB)) throw new SyntaxError(lexer.getLocation(), 80, "");
        getToken(); // skip `{`
        ArrayList<Node> body = new ArrayList<>();
        while (!current(RB)) {
            body.add(new Expr(lexer, resolver).parse());
            skipLineBreaks();
        }
        getToken(); // skip `}`
        return new FunctionNode(lexer.getLocation(), func_name, return_type, args, body);
    }

    private Type convertSymbolIntoType(Symbol symbol) throws Exception {
        if (symbol.equals(Symbol.intern(0, "Int"))) {
            return new IntType();
        } else if (symbol.equals(Symbol.intern(0, "Double"))) {
            return new DoubleType();
        } else if (symbol.equals(Symbol.intern(0, "Bool"))) {
            return new BoolType();
        } else if (symbol.equals(Symbol.intern(0, "String"))) {
            return new StrType();
        } else if (symbol.equals(Symbol.intern(0, "Unit"))) {
            return new UnitType();
        } else {
            throw new SyntaxError(symbol.getLocation(), 76, "`" + symbol + "` 型は定義されていません。");
        }
    }
}
