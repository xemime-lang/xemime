package net.zero918nobita.Xemime.resolver;

/**
 * シンボルの参照の解決に失敗した場合に投げられる例外クラスです。
 * @author Kodai Matsumoto
 */

public class SemanticError extends Exception {
    public SemanticError(int location, int errorCode) {
        super(location + ": シンボルの参照先を解決できません [" + errorCode + "]");
    }
}
