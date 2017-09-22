grammar Parser;

@header {
    package net.zero918nobita.Xemime;
}

prog: (expr '\n')*;
expr: WS? 'let' WS SYMBOL WS? '=' WS? expr WS?
    | WS? expr1 WS? ';'
    | WS? expr1 WS?
    ;
expr1: expr1 WS? '[' WS? expr1 WS? ']'
    | expr2
    ;
expr2: expr2 WS? '(' ( WS? expr1 WS? ( ',' WS? expr1)* )? WS? ')'
    | expr3
    ;
expr3: '!' WS? expr1
    | '+' WS? expr1
    | '-' WS? expr1
    | '++' WS? expr1
    | '--' WS? expr1
    | expr4
    ;
expr4: expr4 WS? '^' WS? expr1
    | expr6
    ;
expr6: expr6 WS? '*' WS? expr1
    | expr6 WS? '/' WS? expr1
    | expr6 WS? '%' WS? expr1
    | expr7
    ;
expr7: expr7 WS? '+' WS? expr1
    | expr7 WS? '-' WS? expr2
    | expr8
    ;
expr8: expr8 WS? '<' WS? expr1
    | expr8 WS? '<=' WS? expr1
    | expr8 WS? '>' WS? expr1
    | expr8 WS? '>=' WS? expr1
    | expr9
    ;
expr9: expr9 WS? '==' WS? expr1
    | expr9 WS? '!=' WS? expr1
    | expr10;
expr10: expr10 WS? '&&' WS? expr1
    | expr11
    ;
expr11: expr11 WS? '||' WS? expr1
    | expr12
    ;
expr12: '(' WS? expr1 WS? ')'
    | SYMBOL WS? '=' WS? expr1
    | SYMBOL WS? '++'
    | SYMBOL WS? '--'
    | SYMBOL
    | INT
    ;
WS: ' '*;
INT: [0-9]+ ;
SYMBOL: [A-Za-z][A-Za-z0-9]*;
