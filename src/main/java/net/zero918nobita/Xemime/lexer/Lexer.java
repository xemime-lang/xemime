package net.zero918nobita.Xemime.lexer;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Str;
import net.zero918nobita.Xemime.entity.Int;

import java.math.BigDecimal;

/**
 * 字句解析器です。
 * @author Kodai Matsumoto
 */

public class Lexer {
    /** 行番号 */
    private int line;

    /** 解析中のトークンの種類 */
    private TokenType tokenType;

    /** 解析中のトークンの値 */
    private Node val;

    /** ソースコードの読み込みに用いる戻り読み機能付き Reader */
    private LexerReader reader;

    /** Reader を設定します。 */
    public Lexer(int n, java.lang.String s) throws Exception {
        line = n;
        reader = new LexerReader(s);
        advance();
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public int getLocation() {
        return line;
    }

    /** 次のトークンを読み込み、種類を記録します。 */
    public boolean advance() throws Exception {
            skipWhiteSpace();
            int c = reader.read();
            if (c < 0) return false;
            switch (c) {
                case ';': // ステートメントの末尾
                    tokenType = TokenType.SEMICOLON;
                    break;
                case ',': // 引数リストまたは仮引数リストの区切り文字
                    tokenType = TokenType.COMMA;
                    break;
                case '+': // 加算演算子
                    c = reader.read();
                    if (c == '+') {
                        tokenType = TokenType.INCREMENT;
                    } else {
                        reader.unread();
                        tokenType = TokenType.ADD;
                    }
                    break;
                case '-':
                    c = reader.read();
                    if (c == '>') {
                        // アロー演算子 ->
                        tokenType = TokenType.ARROW;
                    } else if (c == '-') {
                        tokenType = TokenType.DECREMENT;
                    } else {
                        // 減算演算子 -
                        reader.unread();
                        tokenType = TokenType.SUB;
                    }
                    break;
                case '*': // 乗算演算子
                    tokenType = TokenType.MUL;
                    break;
                case '(': // 演算優先または引数/仮引数リストの左括弧
                    tokenType = TokenType.LP;
                    break;
                case ')': // 演算優先または引数/仮引数リストの右括弧
                    tokenType = TokenType.RP;
                    break;
                case '{': // ブロックの右括弧
                    tokenType = TokenType.LB;
                    break;
                case '}': // ブロックの左括弧
                    tokenType = TokenType.RB;
                    break;
                case '#':
                    tokenType = TokenType.LAMBDA;
                    break;
                case '\n':
                    line ++;
                    tokenType = TokenType.BR;
                    break;
                case '=':
                    c = reader.read();
                    if (c == '=') {
                        c = reader.read();
                        if (c == '=') {
                            // 等値演算子
                            tokenType = TokenType.EQ;
                        } else {
                            // 等価演算子 ==
                            reader.unread();
                            tokenType = TokenType.EQL;
                        }
                    } else {
                        // 代入演算子 =
                        reader.unread();
                        tokenType = TokenType.ASSIGN;
                    }
                    break;
                case '!':
                    c = reader.read();
                    if (c == '=') {
                        // 非等価演算子 !=
                        tokenType = TokenType.NE;
                    } else {
                        // 論理否定演算子 !
                        reader.unread();
                        tokenType = TokenType.NOT;
                    }
                    break;
                case '<':
                    c = reader.read();
                    if (c == '=') {
                        // 比較演算子 <=
                        tokenType = TokenType.LE;
                    } else if (c == '-') {
                        // 属性追加演算子 <-
                        tokenType = TokenType.ATTACH;
                    } else {
                        // 比較演算子 <
                        reader.unread();
                        tokenType = TokenType.L;
                    }
                    break;
                case '>':
                    c = reader.read();
                    if (c == '=') {
                        // 比較演算子 >=
                        tokenType = TokenType.GE;
                    } else {
                        // 比較演算子 >
                        reader.unread();
                        tokenType = TokenType.G;
                    }
                    break;
                case '&':
                    c = reader.read();
                    if (c == '&') {
                        // 論理積演算子
                        tokenType = TokenType.AND;
                    } else {
                        throw new Exception(getLocation() + ": 演算子 & は使えません");
                    }
                    break;
                case '|':
                    c = reader.read();
                    if (c == '|') {
                        // 論理和演算子
                        tokenType = TokenType.OR;
                    } else {
                        throw new Exception(getLocation() + ": 演算子 | は使えません");
                    }
                    break;
                case '^': // 排他的論理和演算子
                    tokenType = TokenType.XOR;
                    break;
                case '/':
                    c = reader.read();
                    if (c == '/') {
                        // 1行コメント
                        skipLineComment();
                        return advance();
                    } else if (c == '*') {
                        // 複数行コメント
                        skipComment();
                        return advance();
                    } else {
                        // 除算演算子
                        reader.unread();
                        tokenType = TokenType.DIV;
                    }
                    break;
                case '"': // 文字列定数の始まりを示すダブルクォート
                    lexString();
                    tokenType = TokenType.STRING;
                    break;
                case '.': // メッセージ式
                    c = reader.read();
                    if (c == '.') {
                        c = reader.read();
                        if (c == '.') {
                            tokenType = TokenType.RANGE3;
                        } else {
                            reader.unread();
                            tokenType = TokenType.RANGE2;
                        }
                    } else {
                        reader.unread();
                        tokenType = TokenType.PERIOD;
                    }
                    break;
                case '$': // 括弧を省略した関数呼び出し
                    tokenType = TokenType.DOLLAR;
                    break;
                case ':': // 属性定義式のメンバ名と値の区切りとなるコロン
                    tokenType = TokenType.COLON;
                    break;
                default:
                    if (Character.isDigit((char) c)) {
                        reader.unread();
                        lexDigit();
                        if (val.getClass() == Int.class) {
                            tokenType = TokenType.INT;
                        }
                        if (val.getClass() == Double.class) {
                            tokenType = TokenType.DOUBLE;
                        }
                    } else if (Character.isJavaIdentifierStart((char)c)) {
                        reader.unread();
                        lexSymbol();
                    } else {
                        throw new Exception(getLocation() + ": 不明なトークンです");
                    }
                    break;
            }
        return true;
    }

    /** 現在解析中のトークンの種類を返します。 */
    public TokenType tokenType() {
        return tokenType;
    }

    /** 現在解析中のトークンの値を返します。 */
    public Node value() {
        return val;
    }

    /**
     * 数値定数の字句解析を行います。
     * @throws Exception 小数型定数の記述中に、複数個の小数点が発見された場合に例外を発生させます。
     */
    private void lexDigit() throws Exception {
        BigDecimal num = new BigDecimal("0");
        boolean point = false; // 小数かどうか
        int decimal_place = 0; // 小数第何位に達しているか
        while (true) {
            int c = reader.read();
            if (c < 0) break;
            if (!Character.isDigit((char)c) && c != '.') {
                reader.unread();
                if (decimal_place == 0 && point) {
                    reader.unread();
                    tokenType = TokenType.INT;
                }
                break;
            }
            if (c == '.' && point) {
                reader.unread(decimal_place);
                tokenType = TokenType.DOUBLE;
                break;
            }
            if (c == '.') point = true; // はじめて小数点が登場したので val に Double を代入するように設定
            if (point && c != '.') {
                decimal_place++;
                num = num.add(new BigDecimal(c - '0').multiply(new BigDecimal("0.1").pow(decimal_place)));
            } else if (c != '.') {
                num = num.multiply(new BigDecimal("10")).add(new BigDecimal(c - '0'));
            }
        }
        if (decimal_place != 0) {
            val = new Double(line, num.doubleValue());
        } else {
            val = new Int(line, num.intValue()); // 整数だったのでint型にキャストしてから Integer を代入
        }
    }

    /**
     * 文字列定数の字句解析を行います。
     * @throws Exception 文字列定数の記述中にソースコードの末端に到達した場合に例外を発生させます。
     */
    private void lexString() throws Exception {
        StringBuilder buf = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c < 0) throw new Exception(getLocation() + ": 文字列定数の記述中にソースコードの末端に到達しました");
            if (c == '"') {
                break;
            } else if (c == '\\') {
                c = reader.read();
                if (c < 0) throw new Exception(getLocation() + ": 文字列中でファイルの終わりに到達しました");
            }
            buf.append((char)c);
        }
        val = new Str(line, buf.toString());
    }

    /**
     * シンボルの字句解析を行い、定数や予約語であればトークンの種類を更新します。
     * @throws Exception シンボルの読み込み中にソースコードの末端に到達した場合に例外を発生させます。
     */
    private void lexSymbol() throws Exception {
        tokenType = TokenType.SYMBOL;
        StringBuilder buf = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c < 0) break;
            if (!Character.isJavaIdentifierPart((char)c)) {
                reader.unread();
                break;
            }
            buf.append((char)c);
        }
        java.lang.String s = buf.toString(); // シンボル名

        switch (s.toUpperCase()) {
            case "UNIT":
                tokenType = TokenType.UNIT;
                break;
            case "T": // 真値
                tokenType = TokenType.T;
                break;
            case "NIL": // 偽値
                tokenType = TokenType.NIL;
                break;
            case "LET": // 変数宣言
                tokenType = TokenType.DECLARE;
                break;
            case "IF":
                tokenType = TokenType.IF;
                break;
            case "ELSE":
                tokenType = TokenType.ELSE;
                break;
            case "SWITCH":
                tokenType = TokenType.SWITCH;
                break;
            case "FOR":
                tokenType = TokenType.FOR;
                break;
            case "IN":
                tokenType = TokenType.IN;
                break;
            case "WHILE":
                tokenType = TokenType.WHILE;
                break;
            case "ATTR": // 属性定義
                tokenType = TokenType.ATTR;
                break;
            case "SUBST": // 実体宣言
                tokenType = TokenType.SUBST;
                break;
            default:
                val = Symbol.intern(line, s); // 既存のシンボルを返すか、新規に生成したシンボルを返します。
        }
    }

    /**
     * 空白文字を読み飛ばします。
     * @throws Exception 後続する文字を読み込んでいるときに LexerReader が例外を発生させる場合があります。
     */
    private void skipWhiteSpace() throws Exception {
        int c = reader.read();
        while (c == ' ' || c == '\t') c = reader.read();
        reader.unread();
    }

    /**
     * 1行コメントを読み飛ばします。
     * @throws Exception コメント中にソースコードの末端に達した場合に例外を発生させます。
     */
    private void skipLineComment() throws Exception {
        int c;
        while ((c = reader.read()) != '\n') {
            if (c < 0) throw new Exception(getLocation() + ": コメント中にソースコードの末端に到達しました");
        }
        reader.unread();
    }

    /**
     * 複数行コメントを読み飛ばします。
     * @throws Exception コメント中にソースコードの末端に達した場合に例外を発生させます。
     */
    private void skipComment() throws Exception {
        int c;
        while (true) {
            c = reader.read();
            if (c == '\n') line++;
            if (c < 0) throw new Exception(getLocation() + ": コメント中にソースコードの末端に到達しました");
            if (c == '*') {
                c = reader.read();
                if (c == '/') break;
            }
        }
    }
}
