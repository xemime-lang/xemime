# データ型

Xemime のデータ型は以下の 14 種類です。

※ ソースコード・ドキュメント等で略記することがある場合は、
括弧内に略称を明記しています。

- [Nix](#nix)
- [Boolean](#bool) (Bool)
- [Integer](#int) (Int)
- [Character](#char) (Char)
- [Float](#float)
- [Double](#double)
- [Long](#long)
- [Procedure](#proc) (Proc)
- [Attribute](#attr) (Attr)
- [Substance](#subst) (Subst)
- [Family](#family)
- [Optional](#optional)

<a name="nix"></a>
## Nix

「無」を表現するデータ型で、取り得る値は ``nix`` だけです。

<a name="bool"></a>
## Boolean (Bool)

「真偽値」を表現するデータ型で、取り得る値は ``t``, ``nil`` の 2 種類です。


<a name="int"></a>
## Integer (Int)

「整数値」を表現するデータ型です。

<a name="float"></a>
## Float

執筆中

<a name="double"></a>
## Double

執筆中

<a name="long"></a>
## Long

執筆中

<a name="proc"></a>
## Procedure (Proc)

後述する「[属性(Attribute)](#attr)」に追加する「プロシージャ」を表現するデータ型です。

<a name="attr"></a>
## Attribute (Attr)

後述する「[実体(Substance)](#subst)」に追加する「属性」を表現するデータ型です。

<a name="subst"></a>
## Substance (Subst)

任意の個数の変数をまとめて名前をつけた「実体」を表現するデータ型です。

<a name="family"></a>
## Family

執筆中

<a name="optional"></a>
## Optional

執筆中
