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
# program: プログラム, expr: 式

program = expr , ";" ;

# simple_expr: 単純式

expr = simple_expr , [ { ( "==" | "!=" | "<" | "<=" | ">" | ">=" ) , simple_expr } ] ;

# term: 項

simple_expr = term , [ { ( "+" | "-" | "||" ) , term } ] ;

# factor: 因子

term = factor , [ { ( "*" | "/" | "&&" ) , factor } ] ;

# first: 一次子, message_expr: メッセージ式, "nil": 偽,
# string: 文字列定数, "t": 真, symbol: シンボル
# ※ "t", "nil" ともに大文字・小文字に区別はありません

factor = first
    | message_expr
    | "nil"
    | string
    | "t"
    | "!" , factor
    | factor , symbol , "=" , expr
    ;

# assignment: 代入式, block: ブロック, declaration: 宣言式,
# function_call: 関数呼び出し, number: 数値定数

first = assignment
    | block
    | declaration
    | function_call
    | number
    | symbol
    | "-" , first
    | "(" , expr , ")"
    ;

assignment = symbol , "=" , expr ;

block = "{" , { expr } , "}" ;

declaration = "let" , symbol , "=" , expr ;

function_call = symbol , "(" , [ expr , [ { "," , expr } ] ] , ")" ;

message_expr = factor , symbol
    | factor , symbol , "(" , [ expr , [ { "," , expr } ] ] , ")"
    ;
```
