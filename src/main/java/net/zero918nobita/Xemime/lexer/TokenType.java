package net.zero918nobita.Xemime.lexer;

/**
 * トークンの種類
 * @author Kodai Matsumoto
 */

public enum TokenType {
    /** セミコロン */
    SEMICOLON,
    /** コロン */
    COLON,
    /** コンマ */
    COMMA,
    /** ソースコードの末端 */
    EOS,

    IF,
    ELSE_IF,
    ELSE,
    SWITCH,
    CASE,
    DEFAULT,
    FOR,
    WHILE,
    BREAK,
    CONTINUE,

    /** unit (無効な値) */
    UNIT,

    /** 真値 */
    T,
    /** 偽値 */
    NIL,

    /** 整数型定数 */
    INT,
    /** 浮動小数点定数 */
    DOUBLE,
    /** 文字列定数 */
    STRING,
    /** シンボル */
    SYMBOL,

    /** 変数宣言 */
    DECLARE,
    /** 代入演算子 */
    ASSIGN,

    /** 属性定義 */
    ATTR,
    /** 実体宣言 */
    SUBST,
    /** &lt;= ( 属性追加演算子 ) */
    ATTACH,

    /** 加算演算子 */
    ADD,
    /** 減算演算子 */
    SUB,
    /** 乗算演算子 */
    MUL,
    /** 除算演算子 */
    DIV,

    /** === */
    EQ,
    /** == */
    EQL,
    /** != */
    NE,
    /** &lt; (Less) */
    L,
    /** &lt;= (Less or Equal) */
    LE,
    /** &gt; (Greater)*/
    G,
    /** &gt;= (Greater or Equal) */
    GE,

    /** 論理積 */
    AND,
    /** 論理和 */
    OR,
    /** 排他的論理和 */
    XOR,
    /** 論理否定 */
    NOT,

    /** 右括弧 */
    LP,
    /** 右括弧 */
    RP,
    /** 左波括弧 */
    LB,
    /** 右波括弧 */
    RB,

    /** lambda */
    LAMBDA,
    /** -&gt; */
    ARROW,

    /** . (メッセージ式) */
    PERIOD,

    /** $ */
    DOLLAR
}
