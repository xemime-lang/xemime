| エラーコード | 部類 | 内容 | 発生場所 ( 先頭に `net.zero918nobita.Xemime.` が付きます ) |
| ---- | ---- | ---- | ---- |
| 1 | Divide By Zero Error | 構文解析中にゼロ除算が行われている箇所を発見しました。 | ``parser.Term#term`` |
| 2* | Semantic Error | シンボルの参照の解決に失敗しました。 | ``resolver.Scope#referVar`` |
| 3 | Fatal Error | 呼び出しが禁止されているメソッドが呼び出されました。 | ``parser.Args#parse`` |
| 4 | Syntax Error | メッセージ式のピリオドの後ろがシンボルではありません。 | ``parser.Factor#parse`` |
| 5 | Syntax Error | メッセージ式の括弧が閉じられていません。 | ``parser.Factor#parse`` |
| 6 | Fatal Exception | シンボルが宣言されていません。 | ``ast.AssignNode#run`` |
| 7 | Syntax Error | ブロック式内のステートメントにセミコロンが付いていません。 | ``parser.Block#parse`` |
| 8 | Syntax Error | 対応する括弧がありません。 | ``parser.First#parse`` |
| 9 | Fatal Exception | 関数呼び出しに失敗しました。 | ``ast.FuncallNode`` |
| 10 | Fatal Exception | 指定された関数は存在しません。 | ``ast.FuncallNode`` |
| 11 | Fatal Exception | 呼び出し対象が関数ではありません。 | ``ast.FuncallNode`` |
| 12 | Fatal Exception | 指定された関数は存在しません。 | ``ast.FuncallNode`` |
| 13 | Fatal Exception | 呼び出し対象が関数ではありません。 | ``ast.FuncallNode`` | 
| 14 | Fatal Exception | 数値以外のデータには単項演算子 ``-`` を適用できません。 | ``ast.MinusNode`` |
| 15 | Fatal Exception | 真偽値以外のデータには単項演算子 ``!`` を適用できません。 | ``ast.NotNode`` |
| 16 | Syntax Error | ``attr`` の後ろに属性名を記述してください。 | ``parser.First#parse`` |
| 17 | Syntax Error | シンボルの後ろに波括弧 ``{`` を記述してください。 | ``parser.First#parse`` |
| 18 | Syntax Error | メンバ名と値の区切りのコロン ``:`` が必要です。 | ``parser.First#parse`` |
| 19 | Syntax Error | 区切りのカンマ ``,`` が必要です。 | ``parser.First#parse`` |
| 20 | Syntax Error | メンバ名と値の区切りのコロン ``:`` が必要です。 | ``parser.First#parse`` |
| 21 | Fatal Exception |  | ``ast.AttrDeclarationNode#run`` |
| 22 | Semantic Error |  | ``resolver.Scope#getTypeOfVariable`` |
| 23 | Syntax Error | ステートメントの末尾にはセミコロンが必要です。 | ``parser.Expr#parse`` |
| 24 | Fatal Exception | ブロック式の戻り値が設定されていません。 | ``ast.BlockNode#run`` |
| 25 | Syntax Error | 不明なトークンが発見されました。 | ``parser.Parser`` |
| 26 | Fatal Exception | 後置デクリメントの評価に失敗しました。 | ``ast.SuffixDecrementNode#run`` |
| 27 | Syntax Error | 条件式の後ろに波括弧 ``{`` を記述してください。 | ``parser.If#parse`` |
| 28 | Fatal Exception | 後置デクリメントの評価に失敗しました。 | ``ast.SuffixDecrementNode#run`` |
| 29 | Syntax Error | ``else`` の後ろに波括弧 ``{`` を記述してください。 | ``parser.If#parse`` |
| 31 | Syntax Error | 不明なトークンが発見されました。 | ``parser.Block#parse`` |
| 32 | Fatal Exception | 後置インクリメントの評価に失敗しました。 | ``ast.SuffixIncrementNode#run`` |
| 33 | Fatal Exception | 後置インクリメントの評価に失敗しました。 | ``ast.SuffixIncrementNode#run`` |
| 34 | Fatal Exception | 前置デクリメントの評価に失敗しました。 | ``ast.PrefixDecrementNode#run`` |
| 35 | Fatal Exception | 前置デクリメントの評価に失敗しました。 | ``ast.PrefixDecrementNode#run`` |
| 36 | Fatal Exception | 前置インクリメントの評価に失敗しました。 | ``ast.PrefixIncrementNode#run`` |
| 37 | Fatal Exception | 前置インクリメントの評価に失敗しました。 | ``ast.PrefixIncrementNode#run`` |
| 38 | Syntax Error | シンボルではない要素に前置インクリメント演算子を付与することはできません。 | ``parser.First#parse`` |
| 39 | Syntax Error | シンボルではない要素に後置インクリメント演算子を付与することはできません。 | ``parser.First#parse`` |
| 40 | Syntax Error | カウンタ変数の後ろには ``in`` キーワードを記述してください。 | ``parser.For#parse`` |
| 41 | Fatal Exception | ``for`` 文の範囲式として指定されたデータの型が不正です。 | ``ast.ForNode#run`` |
| 42 | Fatal Exception | 範囲式の左辺が整数値ではありません。 | ``ast.RangeExprNode#run`` |
| 43 | Fatal Exception | 範囲式の右辺が整数値ではありません。 | ``ast.RangeExprNode#run`` |
| 44 | Syntax Error | 範囲式の後ろに波括弧 ``{`` を記述してください。 | ``parser.For#parse`` |
| 45 | Syntax Error | 条件式の後ろに波括弧 ``{`` を記述してください。 | ``parser.While#parse`` |
| 46 | Syntax Error | 変数宣言式が不正です。宣言する変数の名称を記述してください。 | ``parser.First#parse`` |
| 47 | Syntax Error | 変数宣言式が不正です。代入演算子を使用してください。 | ``parser.First#parse`` |
| 48 | Syntax Error | ``for`` キーワードの後ろにはカウンタ変数を指定してください。 | ``parser.For#parse`` |
| 49 | Syntax Error |  | `resolver.StaticTypeChecker`` |
| 50 | Syntax Error | コロンの `:` の後ろでデータ型を指定してください。 | ``parser.First#run`` |
| 51 | Semantic Error | 代入式の変数の参照先を解決できません。 | ``resolver.Resolver#assignVar`` |
| 52 | Type Error | 後置インクリメント演算子を付与された変数が整数型ではありません。 | ``parser.First#parse`` |
| 53 | Type Error | 後置デクリメント演算子を付与された変数が整数型ではありません。 | ``parser.First#parse`` |
| 54 | Type Error | 前置インクリメント演算子を付与された変数が整数型ではありません。　| ``parser.First#prefixIncrement`` |
| 55 | Type Error | 前置デクリメント演算子を付与された変数が整数型ではありません。 | ``parser.First#prefixDecrement`` |
| 56 | Type Error | 論理否定演算子を真偽型以外の型のデータに適用することはできません。 | ``parser.Factor#parse`` |
| 57 | Type Error |  | ``resolver.StaticTypeChecker`` |
| 58 | Type Error |  | ``resolver.StaticTypeChecker`` |
| 59 | Type Error |  | ``resolver.StaticTypeChecker`` |
| 60 | Fatal Error |  | ``resolver.StaticTypeChecker`` |
| 61 | Type Error |  | ``resolver.StaticTypeChecker`` |
| 62 | Type Error |  | ``parser.First#parse`` |
| 63 | Type Error |  | ``parser.First#parse`` |
| 64 | Type Error |  | ``resolver.StaticTypeChecker`` |
| 65 | Fatal Exception |  | ``entity.Function#exec`` |
| 66 | Syntax Error |  | ``parser.Fn#parse`` |
| 67 | Syntax Error |  | ``parser.Fn#parse`` |
| 68 | Syntax Error | 引数リストが不正です。 | ``parser.Factor#parse`` |
| 69 | Syntax Error | 引数をコンマ ``,`` で区切ってください。 | ``parser.Factor#parse`` |
| 70 | Syntax Error |  | ``parser.Fn#parse`` |
| 71 | Syntax Error | 引数リストが正しく ( ) 括弧で括られていません。 | ``parser.First#parse`` |
| 72 | Syntax Error | 不明なトークンが検出されました。 | ``parser.First#parse`` |
| 73 | Syntax Error | 指定された型は定義されていません。 | ``parser.First#parse`` |
| 74 | Syntax Error |  | ``parser.Fn#parse`` |
| 75 | Syntax Error |  | ``parser.Fn#parse`` |
| 76 | Syntax Error | 指定された型は定義されていません。 | ``parser.Fn#parse`` |
| 77 | Syntax Error |  | ``parser.Fn#parse`` |
| 78 | Syntax Error |  | ``parser.Fn#parse`` |
| 79 | Syntax Error |  | ``parser.Fn#parse`` |
| 80 | Syntax Error |  | ``parser.Fn#parse`` |
| 81 | Syntax Error |  | ``parser.Args#arguments`` |
| 82 | Syntax Error |  | ``parser.Args#arguments`` |
| 83 | Syntax Error |  | ``parser.Args#arguments`` |
| 84 | Syntax Error |  | ``parser.Args#arguments`` |
| 85 | Syntax Error |  | ``parser.Factor#parse`` |
| 86 | Syntax Error |  | ``parser.Factor#parse`` |
| 87 | Type Error | 代入式が不正です。変数の型と代入される値の型が一致しません。 | ``resolver.Resolver#assignVar`` |
| 88 |  |  |  |
| 89 |  |  |  |
| 90 |  |  |  |
| 91 |  |  |  |
| 92 |  |  |  |
| 93 | Syntax Error | 引数をコンマ ``,`` で区切ってください。 | ``parser.Args#arguments`` |
| 94 | Syntax Error | 関数を中置関数化するための2つのバッククォートの間には、シンボル(関数名)を記述してください。 | ``parser.Term#parse`` |
| 95 | Syntax Error | バッククォートで中置関数名が閉じられていません。 | ``parser.Term#parse`` |
| 96 | Fatal Error | シンボルまたは組み込み関数を指定してください。 | ``resolver.StaticTypeChecker#check`` |
| 97 | Type Error | 指定されたシンボルの型が関数型ではありません。 | ``resolver.StaticTypeChecker#check`` |
| 98 | Fatal Exception | ``+`` 演算子は使用できません。 | ``ast.Node#add`` |
| 99 | Fatal Exception | ``-`` 演算子は使用できません。 | ``ast.Node#sub`` |
| 100 | Fatal Exception | ``*`` 演算子は使用できません。 | ``ast.Node#multiply`` |
| 101 | Fatal Exception | ``/`` 演算子は使用できません。 | ``ast.Node#divide`` |
| 102 | Fatal Exception | ``<`` 演算子は使用できません。 | ``ast.Node#less`` |
| 103 | Fatal Exception | ``<=`` 演算子は使用できません。 | ``ast.Node#le`` |
| 104 | Fatal Exception | ``>`` 演算子は使用できません。 | ``ast.Node#greater`` |
| 105 | Fatal Exception | ``>=`` 演算子は使用できません。 | ``ast.Node#ge`` |
| 106 | Fatal Exception | 整数に数値型以外のオブジェクトを足すことはできません。 | ``entity.Int#add`` |
| 107 | Fatal Exception | 整数から数値型以外のオブジェクトを引くことはできません。 | ``entitiy.Int#sub`` |
| 108 | Fatal Exception | 整数に数値型以外のオブジェクトを掛けることはできません。 | ``entity.Int#multiply`` |
| 109 | Fatal Exception | 整数を数値型以外のオブジェクトで割ることはできません。 | ``entity.Int#divide`` |
| 110 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#less`` |
| 111 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#le`` |
| 112 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#greater``|
| 113 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#ge`` |
| 114 | Fatal Exception | ``&&`` 演算子は使用できません。 | ``ast.Node#and`` |
| 115 | Fatal Exception | 論理和演算子は使用できません。 | ``ast.Node#or`` |
| 116 | Fatal Exception | ``^`` 演算子は使用できません。 | ``ast.Node#xor`` |
| 117 | Fatal Exception | 論理積演算子の右辺が真偽値ではありません。 | ``ast.Bool#and`` |
| 118 | Fatal Exception | 論理和演算子の右辺が真偽値ではありません。 | ``ast.Bool#or`` |
| 119 | Fatal Exception | 排他的論理和演算子の右辺が真偽値ではありません。 | ``ast.Bool#xor`` |
| 120 | Fatal Exception | ``+`` 演算子の右辺が数値ではありません。 | ``ast.Double#add`` |
| 121 | Fatal Exception | ``-`` 演算子の右辺が数値ではありません。 | ``ast.Double#sub`` |
| 122 | Fatal Exception | ``*`` 演算子の右辺が数値ではありません。 | ``ast.Double#multiply`` |
| 123 | Fatal Exception | ``/`` 演算子の右辺が数値ではありません。 | ``ast.Double#divide`` |
| 124 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#less`` |
| 125 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#le`` |
| 126 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#greater``|
| 127 | Fatal Exception | 整数を Int / Double 以外の型のデータと比較することはできません。 | ``entity.Int#ge`` |
| 128 | Fatal Exception | Str型オブジェクトに他の型のオブジェクトを加算することはできません。 | ``entity.Str#add`` |
| 129 | Type Error |  |  |
| 130 | Type Error |  |  |
| 131 | Type Error |  |  |
| 132 | Syntax Error |  |  |
| 133 | Syntax Error |  |  |
| 134 | Type Error |  |  |
| 135 | Fatal Exception |  |  |
| 136 | SyntaxError |  |  |
| 137 | Runtime Exception | 配列参照の添え字が ``0`` 未満になっているため、要素を取り出すことができません。 | ``ast.ArrayReferenceNode#run`` |
| 138 | Runtime Exception | 配列参照の添え字が配列の要素数以上になっているため、要素を取り出すことができません。 | ``ast.ArrayReferenceNode#run`` |
| 139 | Fatal Exception |  |  |
| 140 | Fatal Exception |  |  |
| 141 | Type Error |  |  |
| 142 | Type Error |  |  |
| 143 | Type Error |  |  |
