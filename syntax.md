# 構文定義

Xemime インタプリタでは、EBNF ( 拡張バッカス・ナウア記法 ) で示される構文定義に従って開発された構文解析器を用いて、ソースコードを解析しています。

以下に、 EBNF で出現する記号とその意味をまとめた表、そして Xemime の構文定義を示します。

| 記号 | 意味 |
| ---- | ---- |
| = | 定義 |
| , | 連結 |
| ; | 終端 |
| &#x7C; | 区切り |
| [ ... ] | オプション |
| { ... } | 繰り返し |
| ( ... ) | グループ化 |
| " ... " | 文字列 |

```
#program: プログラム, expr: 式, br: 1つ以上の改行
program = [ { expr , [br] } ]

# logical_expr: 論理式, if: if文, for: for文, while: while文,
# fn: fn文 ( 関数定義 ), return: return文
expr = logical_expr
    | if
    | for
    | while
    | fn
    | return
    ;

# arithmetic_expr 算術式
logical_expr = arithmetic_expr , [ { ( "&&" |  "==" | "!=" | "<" | "<=" | ">" | ">=" ) , arithmetic_expr } ] ;

# term: 項
arithmetic_expr = term , [ { ( "+" | "-" | "||" ) , term } ] ;

# factor: 因子
term = factor , [ { ( "*" | "/" | "%" | "^" | "`" , SYMBOL , "`" ) , factor } ]
    | term , ".." , term
    | term , "..." , term
    ;

# first: 一次子, lambda_expr: ラムダ式, NIL: 偽値, STRING: 文字列リテラル, T: 真値
factor = first
    | STRING
    | T
    | NIL
    | lambda_expr
    | "!" , factor
    | function_call
    | message_expr
    ;

# assignment_expr: 代入式, block_expr: ブロック式, declaration_expr: 宣言式
# function_call: 関数呼び出し, NUMBER: 数値リテラル, SYMBOL: シンボル, UNIT: unit値
first = assignment_expr
    | block_expr
    | declaration_expr
    | NUMBER
    | SYMBOL
    | UNIT
    | "++" , SYMBOL
    | "--" , SYMBOL
    | SYMBOL , "++"
    | SYMBOL , "--"
    | "+" , first
    | "-" , first
    | "(" , logical_expr , ")"
    | "[" , [br] , [ expr , [ { [br] , expr } ] , [br] ] , "]"
    ;

assignment_expr = SYMBOL , "=" , logical_expr ;

block_expr = "{" , [br] , expr , [ { [br] , expr } ] , [br] , "}";

declaration_expr = "let" , SYMBOL , [":"] , type ,  "=" , logical_expr ;

function_call = factor , "(" , [ logical_expr , [ { "," , logical_expr } ] ] , ")"
    | factor , "$" , [ logical_expr , [ { "," , logical_expr } ] ]
    ;

message_expr = factor , "." , SYMBOL ;

import_stmt = "import" , STRING , "as" , SYMBOL ;

lambda_expr = "#" , "(", [ SYMBOL , [ { "," , SYMBOL } ] ] , ")", "->", logical_expr
    | "#", [ [ { "," , SYMBOL } ] ] , "->" , logical_expr
    ;

type = "Any"
    | "Bool"
    | Int"
    | "Double"
    | "String"
    | "[Any]"
    | "[Bool]"
    | "[Int]"
    | "[Double]"
    | "[String]"
    ;
    
BR: 改行
br = { BR }

```
