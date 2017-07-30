package net.zero918nobita.Xemime;

import java.io.Reader;
import java.math.BigDecimal;

/**
 * 字句解析器
 * @author Kodai Matsumoto
 */

class Lexer {
    private TokenType tokenType;
    private int tok;
    private X_Object val;
    private LexerReader reader;

    Lexer(Reader r) {
        reader = new LexerReader(r);
    }

    boolean advance() {
        try {
            skipWhiteSpace();
            int c = reader.read();
            if (c < 0) return false;
            switch (c) {
                case ';':
                case '+':
                case '-':
                case '*':
                case '(':
                case ')':
                case '=':
                    tokenType = TokenType.SIGN;
                    tok = c;
                    break;
                case '/':
                    c = reader.read();
                    if (c == '/') {
                        skipLineComment();
                        return advance();
                    } else if (c == '*') {
                        skipComment();
                        return advance();
                    } else {
                        reader.unread();
                        tok = '/';
                    }
                    break;
                case '"':
                    lexString();
                    tokenType = TokenType.STRING;
                    tok = '"';
                    break;
                default:
                    if (Character.isDigit((char) c)) {
                        reader.unread();
                        lexDigit();
                        if (val.getClass() == X_Int.class) {
                            tokenType = TokenType.INT;
                            tok = c;
                        }
                        if (val.getClass() == X_Double.class) {
                            tokenType = TokenType.DOUBLE;
                            tok = c;
                        }
                    } else if (Character.isJavaIdentifierStart((char)c)) {
                        reader.unread();
                        lexSymbol();
                    } else {
                        throw new Exception("数字ではありません");
                    }
                    break;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    int token() {
        return tok;
    }

    TokenType tokenType() {
        return tokenType;
    }

    public X_Object value() {
        return val;
    }

    private void lexDigit() throws Exception {
        BigDecimal num = new BigDecimal("0");
        boolean point = false; // 小数かどうか
        int decimal_place = 0; // 小数第何位に達しているか
        while (true) {
            int c = reader.read();
            if (c < 0) break;
            if (!Character.isDigit((char)c) && c != '.') { // 数字でも小数点でもないならエラー
                reader.unread();
                break;
            }
            if (c == '.' && point) throw new Exception("文法エラー"); // 2つ以上小数点が存在するのでエラー
            if (c == '.' && !point) point = true; // はじめて小数点が登場したので val に Double を代入するように設定
            if (point && c != '.') {
                decimal_place++;
                num = num.add(new BigDecimal(c-'0').multiply(new BigDecimal("0.1").pow(decimal_place)));
            } else if (c != '.') {
                num = num.multiply(new BigDecimal("10")).add(new BigDecimal(c-'0'));
            }
        }
        if (point) {
            val = new X_Double(num.doubleValue());
        } else {
            val = new X_Int(num.intValue()); // 整数だったのでint型にキャストしてから Integer を代入
        }
    }

    private void lexString() throws Exception {
        StringBuilder buf = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c < 0) throw new Exception("文字列中でファイルの終わりに到達しました");
            if (c == '"') {
                break;
            } else if (c == '\\') {
                c = reader.read();
                if (c < 0) throw new Exception("文字列中でファイルの終わりに到達しました");
            }
            buf.append((char)c);
        }
        val = new X_String(buf.toString());
    }

    private void lexSymbol() throws Exception {
        tokenType = TokenType.SYMBOL;
        StringBuilder buf = new StringBuilder();
        while (true) {
            int c = reader.read();
            if (c < 0) throw new Exception("ファイルの終わりに到達しました");
            if (!Character.isJavaIdentifierPart((char)c)) {
                reader.unread();
                break;
            }
            buf.append((char)c);
        }
        String s = buf.toString();
        val = X_Symbol.intern(s);

        /*if (reserved.containsKey(s)) {
            tok = ((Integer)reserved.get(s)).intValue();
        }*/
    }

    private void skipWhiteSpace() throws Exception {
        int c = reader.read();
        while ((c != -1) && Character.isWhitespace((char)c)) c = reader.read();
        reader.unread();
    }

    private void skipLineComment() throws Exception {
        int c;
        while ((c = reader.read()) != '\n') {
            if (c < 0) throw new Exception("コメント中にファイルの末端に到達しました");
        }
        reader.unread();
    }

    private void skipComment() throws Exception {
        int c;
        while (true) {
            c = reader.read();
            if (c < 0) throw new Exception("コメント中にファイルの末端に到達しました");
            if (c == '*') {
                c = reader.read();
                if (c == '/') break;
            }
        }
    }
}
