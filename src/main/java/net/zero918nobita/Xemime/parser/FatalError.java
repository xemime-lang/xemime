package net.zero918nobita.Xemime.parser;

/**
 * 字句解析、構文解析、意味解析中に発生する、重大度の最も高い例外です。<br>
 * 本来発生するはずのないもので、もし発生した場合他の箇所に致命的な不具合を抱えている可能性があります。<br>
 * 詳細はエラーコード表に記載されています。
 * @author Kodai Matsumoto
 */

public class FatalError extends Exception {
    public FatalError(int location, int errorCode) {
        super(location + ": インタプリタ内部の深刻な不具合が発生しました。 [" + errorCode + "]");
    }
}
