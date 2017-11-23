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
    /** 改行 */
    BR,
    /** ソースコードの末端 */
    EOS,

    IF,
    ELSE,
    SWITCH,
    FOR,
    IN,
    WHILE,
    FN,
    RETURN,

    /** unit (無効な値) */
    UNIT,

    /** backquote (中置関数) */
    BACKQUOTE,

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
    /** 剰余演算子 */
    MOD,

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
    /** 左角括弧 */
    LSB,
    /** 右角括弧 */
    RSB,

    INCREMENT,
    DECREMENT,

    /** .. */
    RANGE2,
    /** ... */
    RANGE3,

    /** lambda */
    LAMBDA,
    /** -&gt; */
    ARROW,

    /** . (メッセージ式) */
    PERIOD,

    /** $ */
    DOLLAR
}
