package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.interpreter.Main;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.ast.DotAssignNode のテストクラスです。
 * @author Kodai Matsumoto
 */

public class DotAssignNodeTest {
    private String br = System.lineSeparator();

    @Test
    public void test() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Main.exec(
                "let obj = Object.clone()\n" +
                        "obj.a = 2\n" +
                        "println $ obj.a\n"
        );
        assertThat(out.toString(), is("2" + br));
    }
}
