# Xemime 2.0

[![Build Status](https://travis-ci.org/0918nobita/Xemime-2.png)](https://travis-ci.org/0918nobita/Xemime-2)  [![Coverage Status](https://coveralls.io/repos/github/0918nobita/Xemime-2/badge.svg?branch=master)](https://coveralls.io/github/0918nobita/Xemime-2?branch=master)  [![Packagist](https://img.shields.io/packagist/l/doctrine/orm.svg)]()

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
Core.println((1 == 1.0) && Core.if(3 > 2, t, nil)); // -> T

obj := Object.clone();
obj.property := 2;
obj.method := lambda(x) { x * 3; };

Core.println(obj.property); // -> 2
Core.println(obj.method); // -> <Lambda>
Core.println(obj.method(5)); // -> 15
```
