| エラーコード | 部類 | 内容 | 発生場所 ( 先頭に `net.zero918nobita.Xemime.` が付きます ) |
| ---- | ---- | ---- | ---- |
| 1 | DivideByZeroError | 構文解析中にゼロ除算が行われている箇所を発見しました。 | ``parser.Term#term`` |
| 2 | SemanticError | シンボルの参照の解決に失敗しました。 | ``resolver.Scope#referVar`` |
| 3 | FatalError | 呼び出しが禁止されているメソッドが呼び出されました。 | ``parser.Args#parse`` |
| 4 | SyntaxError | メッセージ式のピリオドの後ろがシンボルではありません。 | ``parser.Factor#parse`` |
| 5 | SyntaxError | メッセージ式の括弧が閉じられていません。 | ``parser.Factor#parse`` |
| 6 | FatalException | シンボルが宣言されていません。 | ``ast.AssignNode#run`` |
| 7 | SyntaxError | ブロック式内のステートメントにセミコロンが付いていません。 | ``parser.Block#parse`` |
| 8 | SyntaxError | 対応する括弧がありません。 | ``parser.First#parse`` |
| 9 | FatalException | 関数呼び出しに失敗しました。 | ``ast.FuncallNode`` |
| 10 | FatalException | 指定された関数は存在しません。 | ``ast.FuncallNode`` |
| 11 | FatalException | 呼び出し対象が関数ではありません。 | ``ast.FuncallNode`` |
| 12 | FatalException | 指定された関数は存在しません。 | ``ast.FuncallNode`` |
| 13 | FatalException | 呼び出し対象が関数ではありません。 | ``ast.FuncallNode`` | 
| 14 | FatalException | 数値以外のデータには単項演算子 ``-`` を適用できません。 | ``ast.MinusNode`` |
| 15 | FatalException | 真偽値以外のデータには単項演算子 ``!`` を適用できません。 | ``ast.NotNode`` |
| 16 | SyntaxError | ``attr`` の後ろに属性名を記述してください。 | ``parser.First#parse`` |
| 17 | SyntaxError | シンボルの後ろに波括弧 ``{`` を記述してください。 | ``parser.First#parse`` |
| 18 | SyntaxError | メンバ名と値の区切りのコロン ``:`` が必要です。 | ``parser.First#parse`` |
| 19 | SyntaxError | 区切りのカンマ ``,`` が必要です。 | ``parser.First#parse`` |
| 20 | SyntaxError | メンバ名と値の区切りのコロン ``:`` が必要です。 | ``parser.First#parse`` |
| 21 | FatalException |  | ``ast.AttrDeclarationNode#run`` |
| 22 | SemanticError |  | ``resolver.Scope#getTypeOfVariable`` |
| 23 | SyntaxError | ステートメントの末尾にはセミコロンが必要です。 | ``parser.Expr#parse`` |
| 24 | FatalException | ブロック式の戻り値が設定されていません。 | ``ast.BlockNode#run`` |
| 25 | SyntaxError | 不明なトークンが発見されました。 | ``parser.Parser`` |
| 26 | FatalException | 後置デクリメントの評価に失敗しました。 | ``ast.SuffixDecrementNode#run`` |
| 27 | SyntaxError | 条件式の後ろに波括弧 ``{`` を記述してください。 | ``parser.If#parse`` |
| 28 | FatalException | 後置デクリメントの評価に失敗しました。 | ``ast.SuffixDecrementNode#run`` |
| 29 | SyntaxError | ``else`` の後ろに波括弧 ``{`` を記述してください。 | ``parser.If#parse`` |
| 31 | SyntaxError | 不明なトークンが発見されました。 | ``parser.Block#parse`` |
| 32 | FatalException | 後置インクリメントの評価に失敗しました。 | ``ast.SuffixIncrementNode#run`` |
| 33 | FatalException | 後置インクリメントの評価に失敗しました。 | ``ast.SuffixIncrementNode#run`` |
| 34 | FatalException | 前置デクリメントの評価に失敗しました。 | ``ast.PrefixDecrementNode#run`` |
| 35 | FatalException | 前置デクリメントの評価に失敗しました。 | ``ast.PrefixDecrementNode#run`` |
| 36 | FatalException | 前置インクリメントの評価に失敗しました。 | ``ast.PrefixIncrementNode#run`` |
| 37 | FatalException | 前置インクリメントの評価に失敗しました。 | ``ast.PrefixIncrementNode#run`` |
| 38 | SyntaxError | シンボルではない要素に前置インクリメント演算子を付与することはできません。 | ``parser.First#parse`` |
| 39 | SyntaxError | シンボルではない要素に後置インクリメント演算子を付与することはできません。 | ``parser.First#parse`` |
| 40 | SyntaxError | カウンタ変数の後ろには ``in`` キーワードを記述してください。 | ``parser.For#parse`` |
| 41 | FatalException | ``for`` 文の範囲式として指定されたデータの型が不正です。 | ``ast.ForNode#run`` |
| 42 | FatalException | 範囲式の左辺が整数値ではありません。 | ``ast.RangeExprNode#run`` |
| 43 | FatalException | 範囲式の右辺が整数値ではありません。 | ``ast.RangeExprNode#run`` |
| 44 | SyntaxError | 範囲式の後ろに波括弧 ``{`` を記述してください。 | ``parser.For#parse`` |
| 45 | SyntaxError | 条件式の後ろに波括弧 ``{`` を記述してください。 | ``parser.While#parse`` |
| 46 | SyntaxError | 変数宣言式が不正です。宣言する変数の名称を記述してください。 | ``parser.First#parse`` |
| 47 | SyntaxError | 変数宣言式が不正です。代入演算子を使用してください。 | ``parser.First#parse`` |
| 48 | SyntaxError | ``for`` キーワードの後ろにはカウンタ変数を指定してください。 | ``parser.For#parse`` |
| 49 | SyntaxError |  | `resolver.StaticTypeChecker`` |
| 50 | SyntaxError | コロンの `:` の後ろでデータ型を指定してください。 | ``parser.First#run`` |
| 51 | SemanticError | 代入式の変数の参照先を解決できません。 | ``resolver.Resolver#assignVar`` |
| 52 | TypeError | 後置インクリメント演算子を付与された変数が整数型ではありません。 | ``parser.First#parse`` |
| 53 | TypeError | 後置デクリメント演算子を付与された変数が整数型ではありません。 | ``parser.First#parse`` |
| 54 | TypeError | 前置インクリメント演算子を付与された変数が整数型ではありません。　| ``parser.First#prefixIncrement`` |
| 55 | TypeError | 前置デクリメント演算子を付与された変数が整数型ではありません。 | ``parser.First#prefixDecrement`` |
| 56 | TypeError | 論理否定演算子を真偽型以外の型のデータに適用することはできません。 | ``parser.Factor#parse`` |
| 57 | TypeError |  | ``resolver.StaticTypeChecker`` |
| 58 | TypeError |  | ``resolver.StaticTypeChecker`` |
| 59 | TypeError |  | ``resolver.StaticTypeChecker`` |
| 60 | FatalError |  | ``resolver.StaticTypeChecker`` |
| 61 | TypeError |  | ``resolver.StaticTypeChecker`` |
| 62 | TypeError |  | ``parser.First#parse`` |
| 63 | TypeError |  | ``parser.First#parse`` |
| 64 | TypeError |  | ``resolver.StaticTypeChecker`` |
| 65 | FatalException |  | ``entity.Function#exec`` |
| 66 | SyntaxError |  | ``parser.Fn#parse`` |
| 67 | SyntaxError |  | ``parser.Fn#parse`` |
| 68 | SyntaxError | 引数リストが不正です。 | ``parser.Factor#parse`` |
| 69 | SyntaxError | 引数をコンマ ``,`` で区切ってください。 | ``parser.Factor#parse`` |
| 70 | SyntaxError |  | ``parser.Fn#parse`` |
| 71 | SyntaxError | 引数リストが正しく ( ) 括弧で括られていません。 | ``parser.First#parse`` |
| 72 | SyntaxError | 不明なトークンが検出されました。 | ``parser.First#parse`` |
| 73 | SyntaxError | 指定された型は定義されていません。 | ``parser.First#parse`` |
| 74 | SyntaxError |  | ``parser.Fn#parse`` |
| 75 | SyntaxError |  | ``parser.Fn#parse`` |
| 76 | SyntaxError | 指定された型は定義されていません。 | ``parser.Fn#parse`` |
| 77 | SyntaxError |  | ``parser.Fn#parse`` |
| 78 | SyntaxError |  | ``parser.Fn#parse`` |
| 79 | SyntaxError |  | ``parser.Fn#parse`` |
| 80 | SyntaxError |  | ``parser.Fn#parse`` |
| 81 | SyntaxError |  | ``parser.Args#arguments`` |
| 82 | SyntaxError |  | ``parser.Args#arguments`` |
| 83 | SyntaxError |  | ``parser.Args#arguments`` |
| 84 | SyntaxError |  | ``parser.Args#arguments`` |
| 85 | SyntaxError |  | ``parser.Factor#parse`` |
| 86 | SyntaxError |  | ``parser.Factor#parse`` |
| 87 | TypeError | 代入式が不正です。変数の型と代入される値の型が一致しません。 | ``resolver.Resolver#assignVar`` |
