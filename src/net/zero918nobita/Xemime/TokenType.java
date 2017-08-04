package net.zero918nobita.Xemime;

/**
 * トークンの種類
 * @author Kodai Matsumoto
 */

enum TokenType {
            /** 真値 */ T,
            /** 偽値 */ NIL,
       /** 整数型定数 */ INT,
    /** 浮動小数点定数 */ DOUBLE,
       /** 文字列定数 */ STRING,
         /** シンボル */ SYMBOL,
       /** 代入演算子 */ ASSIGN,
       /** セミコロン */ SEMICOLON,
/** ソースコードの末端 */ EOS,
       /** 加算演算子 */ ADD,
       /** 減算演算子 */ SUB,
       /** 乗算演算子 */ MUL,
       /** 除算演算子 */ DIV,
             /** == */ EQ,
             /** != */ NE,
           /** &lt; */ L,
          /** &lt;= */ LE,
           /** &gt; */ G,
          /** &gt;= */ GE,
          /** 論理積 */ AND,
          /** 論理和 */ OR,
     /** 排他的論理和 */ XOR,
        /** 論理否定 */ NOT,
          /** 右括弧 */ LP,
          /** 右括弧 */ RP,
        /** 左波括弧 */ LB,
        /** 右波括弧 */ RB
}
