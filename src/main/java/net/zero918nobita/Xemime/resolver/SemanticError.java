package net.zero918nobita.Xemime.resolver;

import net.zero918nobita.Xemime.ast.Symbol;

/**
 * シンボルの参照の解決に失敗した場合に投げられる例外クラスです。
 * @author Kodai Matsumoto
 */

public class SemanticError extends Exception {
    public SemanticError(int location, int errorCode, Symbol sym) {
        super(location + ": シンボル `" + sym + "` の参照先を解決できません [" + errorCode + "]");
    }
}
