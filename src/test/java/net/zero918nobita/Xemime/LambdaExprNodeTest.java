package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.ExprNode;
import net.zero918nobita.Xemime.ast.LambdaExprNode;
import net.zero918nobita.Xemime.ast.Node;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.entity.Closure;
import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.interpreter.Main;
import net.zero918nobita.Xemime.lexer.TokenType;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.ast.LambdaExprNode のテストクラスです。
 * @author Kodai Matsumoto
 */

public class LambdaExprNodeTest {
    private String br = System.lineSeparator();

    @Test
    public void test() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Main.exec(
                "let f = #x -> x + 2\n" +
                        "println $ f $ 2\n" +
                        "println $ f $ 3\n"
        );
        assertThat(out.toString(), is("4" + br + "5" + br));
    }
}
