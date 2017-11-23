package net.zero918nobita.Xemime.lexer;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Str;
import net.zero918nobita.Xemime.entity.Int;

import java.math.BigDecimal;

import static net.zero918nobita.Xemime.lexer.TokenType.*;

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
                tokenType = SEMICOLON;
                break;
            case ',': // 引数リストまたは仮引数リストの区切り文字
                tokenType = COMMA;
                break;
            case '+': // 加算演算子
                c = reader.read();
                if (c == '+') {
                    tokenType = INCREMENT;
                } else {
                    reader.unread();
                    tokenType = ADD;
                }
                break;
            case '-':
                c = reader.read();
                if (c == '>') {
                    // アロー演算子 ->
                    tokenType = ARROW;
                } else if (c == '-') {
                    tokenType = DECREMENT;
                } else {
                    // 減算演算子 -
                    reader.unread();
                    tokenType = SUB;
                }
                break;
            case '*': // 乗算演算子
                tokenType = MUL;
                break;
            case '(': // 演算優先または引数/仮引数リストの左括弧
                tokenType = LP;
                break;
            case ')': // 演算優先または引数/仮引数リストの右括弧
                tokenType = RP;
                break;
            case '{': // ブロックの右括弧
                tokenType = LB;
                break;
            case '}': // ブロックの左括弧
                tokenType = RB;
                break;
            case '[':
                tokenType = LSB;
                break;
            case ']':
                tokenType = RSB;
                break;
            case '#':
                tokenType = LAMBDA;
                break;
            case '\n':
                line ++;
                tokenType = BR;
                break;
            case '=':
                c = reader.read();
                if (c == '=') {
                    c = reader.read();
                    if (c == '=') {
                        // 等値演算子
                        tokenType = EQ;
                    } else {
                        // 等価演算子 ==
                        reader.unread();
                        tokenType = EQL;
                    }
                } else {
                    // 代入演算子 =
                    reader.unread();
                    tokenType = ASSIGN;
                }
                break;
            case '!':
                c = reader.read();
                if (c == '=') {
                    // 非等価演算子 !=
                    tokenType = NE;
                } else {
                    // 論理否定演算子 !
                    reader.unread();
                    tokenType = NOT;
                }
                break;
            case '<':
                c = reader.read();
                if (c == '=') {
                    // 比較演算子 <=
                    tokenType = LE;
                } else if (c == '-') {
                    // 属性追加演算子 <-
                    tokenType = ATTACH;
                } else {
                    // 比較演算子 <
                    reader.unread();
                    tokenType = L;
                }
                break;
            case '>':
                c = reader.read();
                if (c == '=') {
                    // 比較演算子 >=
                    tokenType = GE;
                } else {
                    // 比較演算子 >
                    reader.unread();
                    tokenType = G;
                }
                break;
            case '&':
                c = reader.read();
                if (c == '&') {
                    // 論理積演算子
                    tokenType = AND;
                } else {
                    throw new Exception(getLocation() + ": 演算子 & は使えません");
                }
                break;
            case '|':
                c = reader.read();
                if (c == '|') {
                    // 論理和演算子
                    tokenType = OR;
                } else {
                    throw new Exception(getLocation() + ": 演算子 | は使えません");
                }
                break;
            case '^': // 排他的論理和演算子
                tokenType = XOR;
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
                    tokenType = DIV;
                }
                break;
            case '%': // 剰余演算子
                tokenType = MOD;
                break;
            case '"': // 文字列定数の始まりを示すダブルクォート
                lexString();
                tokenType = STRING;
                break;
            case '`': // 中置関数名
                c = reader.read();
                if (Character.isJavaIdentifierStart((char)c)) {
                    reader.unread();
                    lexSymbol();
                } else throw new Exception("");
                c = reader.read();
                if (c != '`') throw new Exception("");
                tokenType = BACKQUOTE;
                break;
            case '.': // メッセージ式
                c = reader.read();
                if (c == '.') {
                    c = reader.read();
                    if (c == '.') {
                        tokenType = RANGE3;
                    } else {
                        reader.unread();
                        tokenType = RANGE2;
                    }
                } else {
                    reader.unread();
                    tokenType = PERIOD;
                }
                break;
            case '$': // 括弧を省略した関数呼び出し
                tokenType = DOLLAR;
                break;
            case ':': // 属性定義式のメンバ名と値の区切りとなるコロン
                tokenType = COLON;
                break;
            default:
                if (Character.isDigit((char) c)) {
                    reader.unread();
                    lexDigit();
                    if (val.getClass() == Int.class) tokenType = INT;
                    else if (val.getClass() == Double.class) tokenType = DOUBLE;
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
                    tokenType = INT;
                }
                break;
            }
            if (c == '.' && point) {
                reader.unread(decimal_place);
                tokenType = DOUBLE;
                break;
            }
            if (c == '.') point = true; // はじめて小数点が登場したので val に DoubleType を代入するように設定
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
        tokenType = SYMBOL;
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
                tokenType = UNIT;
                break;
            case "T": // 真値
                tokenType = T;
                break;
            case "NIL": // 偽値
                tokenType = NIL;
                break;
            case "LET": // 変数宣言
                tokenType = DECLARE;
                break;
            case "IF":
                tokenType = IF;
                break;
            case "ELSE":
                tokenType = ELSE;
                break;
            case "SWITCH":
                tokenType = SWITCH;
                break;
            case "FOR":
                tokenType = FOR;
                break;
            case "IN":
                tokenType = IN;
                break;
            case "WHILE":
                tokenType = WHILE;
                break;
            case "FN":
                tokenType = FN;
                break;
            case "RETURN":
                tokenType = RETURN;
                break;
            case "ATTR": // 属性定義
                tokenType = ATTR;
                break;
            case "SUBST": // 実体宣言
                tokenType = SUBST;
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
