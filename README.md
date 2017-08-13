# Xemime 2.0

[![Build Status](https://travis-ci.org/0918nobita/Xemime-2.png)](https://travis-ci.org/0918nobita/Xemime-2)  [![Coverage Status](https://coveralls.io/repos/github/0918nobita/Xemime-2/badge.svg?branch=master)](https://coveralls.io/github/0918nobita/Xemime-2?branch=master)  [![codebeat badge](https://codebeat.co/badges/d82dec15-a4ee-4bf3-a6d2-41022a1812f6)](https://codebeat.co/projects/github-com-0918nobita-xemime-2-master)  [![Packagist](https://img.shields.io/packagist/l/doctrine/orm.svg)]()

Xemime は、さまざまな言語から影響を受けたプログラミング言語です。<br>
純粋なプロトタイプベースオブジェクト指向言語であり、<br>
小規模のソフトウェアを開発することを目的としています。

| 項目 | 内容 |
| --- | --- |
| パラダイム | プロトタイプベースオブジェクト指向、関数型 |
| 型付け | 強い動的型付け |
| 影響を受けた言語 | Io, JavaScript, Lisp, Ruby, Swift |
| ライセンス | MIT |
| 拡張子 | ``.xemime`` |

# サンプル

```
let x = 2;

println((1 + x) * (3 - 4) + 6 / x);  // -> 0
println((1 == 1.0) && if(3 > 2, t, nil)); // -> T

let obj = Object.clone();
obj.property = 2;
obj.method = lambda(x) { x * 3; };

println(obj.property); // -> 2
println(obj.method); // -> <Lambda>
println(obj.method(5)); // -> 15
```

# インストール方法

安定版はまだリリースできていません。<br>
アルファ版を随時 [Releases](https://github.com/0918nobita/Xemime-2/releases) で公開しています。

# 開発方法

| 項目 | 内容 |
| ---- | ---- |
| 開発言語 | Java 1.8 |
| プロジェクト管理ツール | Maven |
| 単体テストフレームワーク | JUnit 4 |
| コードカバレッジライブラリ | JaCoCo ( ``jacoco-maven-plugin`` ) |
| CI サービス | Travis CI ( ``.travis.yml`` ) |
| カバレッジレポート共有サービス | Coveralls ( ``coveralls-maven-plugin`` ) |
| ドキュメントジェネレータ | GitBook ( npm : ``package.json`` ) |
