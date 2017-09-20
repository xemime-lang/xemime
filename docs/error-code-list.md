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
| 16 | SyntaxError |  | ``parser.First#parse`` |
| 17 | SyntaxError |  | ``parser.First#parse`` |
| 18 | SyntaxError |  | ``parser.First#parse`` |
| 19 | SyntaxError |  | ``parser.First#parse`` |
| 20 | SyntaxError |  | ``parser.First#parse`` |
| 21 | FatalException |  | ``ast.AttrDeclarationNode#run`` |
| 22 | SemanticError |  | ``resolver.Scope#getTypeOfVariable`` |
