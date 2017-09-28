package net.zero918nobita.Xemime.parser;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.lexer.Lexer;
import net.zero918nobita.Xemime.lexer.TokenType;
import net.zero918nobita.Xemime.resolver.Resolver;
import net.zero918nobita.Xemime.type.FuncType;

import java.util.ArrayList;

/**
 * 再帰下降型の構文解析器です。
 * @author Kodai Matsumoto
 */

public class Parser {
    private int line = 1;

    /** 字句解析器 */
    private Lexer lexer;

    /** 解析中のシンボルの種類 */
    private TokenType tokenType;

    /** 参照解決を担当する */
    private Resolver resolver;

    public Parser() throws Exception {
        resolver = new Resolver();
        // 組み込みオブジェクトや省略表記のメソッドのシンボルを resolver に登録することで、
        // それらのシンボルに対する参照解決の失敗を防ぎます。
        resolver.declareVar(new FuncType(), Symbol.intern(0, "Core"));
        resolver.declareVar(new FuncType(), Symbol.intern(0, "Object"));
        resolver.declareVar(new FuncType(), Symbol.intern(0, "print"));
        resolver.declareVar(new FuncType(), Symbol.intern(0, "println"));
        resolver.declareVar(new FuncType(), Symbol.intern(0, "exit"));
    }

    /** 次のトークンをレキサを介して取得し、その種類を記録します。 */
    private void getToken() throws Exception {
        if (lexer.advance()) {
            tokenType = lexer.tokenType();
        } else {
            tokenType = TokenType.EOS;
        }
    }

    /**
     * 構文解析を開始します。
     * @param str ソースコード
     * @return 評価結果
     */
    public ArrayList<Node> parse(String str) throws Exception {
        ArrayList<Node> result = new ArrayList<>();
        lexer = new Lexer(line, str.replaceAll("\r\n|\r", "\n"));
        if (str.replaceAll("\\s", "").equals("")) return result;
        Node code;
        tokenType = null;
        while (tokenType != TokenType.EOS) {
            code = new Expr(lexer, resolver).parse();
            if (lexer.tokenType() != TokenType.BR && lexer.tokenType() != TokenType.EOS) throw new SyntaxError(lexer.getLocation(), 25, "不明なトークン `" + lexer.value() + "` が発見されました");
            if (code != null) result.add(code);
            getToken();
        }
        line = lexer.getLocation();
        return result;
    }

    public void goDown(int n) {
        line += n;
    }

    public int getLocation() {
        return line;
    }
}
