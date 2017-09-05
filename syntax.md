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
#program: プログラム, stmt: 文
program = [ { stmt } ] ;

# for_stmt: for文, if_stmt: if文, import_stmt: インポート文, logical_expr: 論理式
# switch_stmt: switch文, while_stmt: while文
stmt = for_stmt
    | if_stmt
    | import_stmt
    | logical_expr , ";"
    | switch_stmt
    | while_stmt
    ;

# arithmetic_expr 算術式
logical_expr = arithmetic_expr , [ { ( "==" | "!=" | "<" | "<=" | ">" | ">=" ) , arithmetic_expr } ] ;

# term: 項
arithmetic_expr = term , [ { ( "+" | "-" | "||" ) , term } ] ;

# factor: 因子
term = factor , [ { ( "*" | "/" | "&&" ) , factor } ] ;

# first: 一次子, lambda_expr: ラムダ式, NIL: 偽値, STRING: 文字列リテラル, T: 真値
factor = first
    | lambda_expr
    | NIL
    | STRING
    | T
    | "!" , factor
    ;

# assignment_expr: 代入式, block_expr: ブロック式, declaration_expr: 宣言式
# function_call: 関数呼び出し, NUMBER: 数値リテラル, SYMBOL: シンボル
first = assignment_expr
    | block_expr
    | declaration_expr
    | function_call
    | NUMBER
    | SYMBOL
    | "-" , first
    | "(" , expr , ")"
    ;

assignment_expr = SYMBOL , "=" , logical_expr ;

block_expr = "{" , logical_expr , [ ";" ] , "}"
    | "{" , logical_expr , { ";" , logical_expr } , [ ";" ] , "}"
    ;

declaration_expr = "let" , SYMBOL , "=" , logical_expr ;

function_call = first , "(" , [ logical_expr , [ { "," , logical_expr } ] ] , ")" ;

import_stmt = "import" , STRING , "as" , SYMBOL ;

lambda_expr = "#" , "(", [ SYMBOL , [ { "," , SYMBOL } ] ] , ")", "->", logical_expr
    | "#", [ [ { "," , SYMBOL } ] ] , "->" , logical_expr
    ;

```
