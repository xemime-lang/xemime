| エラーコード | 部類 | 内容 | 発生場所 ( 先頭に `net.zero918nobita.Xemime.` が付きます ) |
| ---- | ---- | ---- | ---- |
| 1 | DivideByZeroError | 構文解析中にゼロ除算が行われている箇所を発見しました。 | ``parser.Term#term`` |
| 2 | SemanticError | シンボルの参照の解決に失敗しました。 | ``resolver.Scope#referVar`` |
| 3 | FatalError | 呼び出しが禁止されているメソッドが呼び出されました。 | ``parser.Args#parse`` |
| 4 | SyntaxError | メッセージ式のピリオドの後ろがシンボルではありません。 | ``parser.Factor#parse`` |
| 5 | SyntaxError | メッセージ式の括弧が閉じられていません。 | ``parser.Factor#parse`` |
| 6 | FatalException | シンボルが宣言されていません。 | ``ast.AssignNode#run`` |
| 7 | SyntaxError | ブロック式内のステートメントにセミコロンが付いていません。 | ``parser.Block#parse`` |
