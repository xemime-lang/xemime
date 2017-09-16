package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.interpreter.Main;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.ast.AssignNode のテストクラスです。
 * @author Kodai Matsumoto
 */

public class AssignNodeTest {
    private String br = System.lineSeparator();

    @Test
    public void test() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Main.exec(
                "let num = 1;\n" +
                        "println $ num;\n" +
                        "num = 2;\n" +
                        "println $ num;\n"
        );
        assertThat(out.toString(), is("1" + br + "2" + br));
    }
}
