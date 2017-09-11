package net.zero918nobita.Xemime.parser;

/**
 * ゼロ除算例外<br>
 * 構文解析中に、「2 / 0」など明らかにゼロで割っている数式を発見した場合に投げられます。
 * @author Kodai Matsumoto
 */

class DivideByZeroError extends Exception {
    DivideByZeroError(int location, int errorCode) {
        super(location + ": ゼロ除算を検出しました [" + errorCode + "]");
    }
}
