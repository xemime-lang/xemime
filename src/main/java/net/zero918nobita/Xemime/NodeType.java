package net.zero918nobita.Xemime;

/**
 * ノードの種類を表す列挙型クラスです。
 * @author Kodai Matsumoto
 */

public enum NodeType {
    NODE,
    BOOL,
    INT,
    DOUBLE,
    STR,
    ARRAY,
    NIX,
    ADDRESS,
    SYMBOL,
    FUNC,
    CLOSURE,
    ASSIGN_NODE,
    BLOCK,
    DECLARATION,
    FN,
    RETURN,
    EXPR,
    MINUS,
    NOT,
    PREFIX_INC,
    PREFIX_DEC,
    SUFFIX_INC,
    SUFFIX_DEC,
    FUNCALL,
    LAMBDA_EXPR,
    RANGE_EXPR,
    IF,
    FOR,
    WHILE,
    DOT_ASSIGN,
    DOT_CALL,
    DOT_EXPR
}
