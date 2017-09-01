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
assignment = symbol , "=" , expr ;

block = "{" , { expr } , "}" ;

declaration = "let" , symbol , "=" , expr ;

expr = simple_expr , [ { ( "==" | "!=" | "<" | "<=" | ">" | ">=" ) , simple_expr } ] ;

factor = first
    | lambda_expr
    | NIL
    | STRING
    | T
    | "!" , factor
    ;

first = assignment
    | block
    | declaration
    | function_call
    | NUMBER
    | SYMBOL
    | "-" , first
    | "(" , expr , ")"
    ;

function_call = first , "(" , [ expr , [ { "," , expr } ] ] , ")" ;

import_stmt = "import" , STRING , "as" , SYMBOL ;

lambda_expr = "#" , "(", [ symbol , [ { "," , symbol } ] ] , ")", "->", expr
    | "#", [ [ { "," , symbol } ] ] , "->" , expr
    ;

program = [ { import_stmt } ] , [ { function_stmt } ] ;

SYMBOL = alphabet , [ { ( alphabet | digit ) } ] ;

simple_expr = term , [ { ( "+" | "-" | "||" ) , term } ] ;

term = factor , [ { ( "*" | "/" | "&&" ) , factor } ] ;
```
