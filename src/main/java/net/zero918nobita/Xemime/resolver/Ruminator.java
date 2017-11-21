package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.Symbol;

import java.util.ArrayList;

/**
 * 未解決シンボルを再解決します。
 * @author Kodai Matsumoto
 */

public class Ruminator {
    public static void ruminate(ArrayList<Symbol> symbols, Resolver resolver) throws SemanticError {
        for (Symbol sym : symbols)
            if (!resolver.referVar(sym.getLocation(), sym))
                // Semantic Error - シンボルの参照の解決に失敗しました。
                throw new SemanticError(sym.getLocation(), 2, sym);
    }
}
