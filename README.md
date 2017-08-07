# Xemime 2.0

| 項目 | 内容 |
| --- | --- |
| パラダイム | プロトタイプベースオブジェクト指向、関数型 |
| 型付け | 強い動的型付け |
| 影響を受けた言語 | Io, JavaScript, Lisp, Ruby, Swift |
| ライセンス | MIT |
| 拡張子 | ``.xemime`` |

# サンプル

```
x := 2;

Core.println((1 + x) * (3 - 4) + 6 / x);  // -> 0

Core.println(T == T);              // -> T
Core.println(T == NIL);            // -> NIL
Core.println(1.0 == 1);            // -> T
Core.println(1.1 == 1);            // -> NIL
Core.println("hello" == "hello");  // -> T

x = x + 1;

Core.println(2 < x);    // -> T
Core.println(2.0 > 2);  // -> NIL
Core.println(1 >= 1);   // -> T

Core.println((1 != 1) || (2 > 1));    // -> T

Core.println((2 > 3) && (1 == 1.0));  // -> NIL

Core.println((4 > 2) ^ (5 > 3));      // -> NIL
Core.println((4 > 2) ^ (5 < 3));      // -> T

square := lambda(x) { x * x; };
Core.println(square(5));    // -> 25
Core.println(square(5.0));  // -> 25.0

xor := lambda(b1, b2) { b1 ^ b2; };
Core.println(xor(t, nil));    // -> T
Core.println(xor(nil, nil));  // -> NIL
```
