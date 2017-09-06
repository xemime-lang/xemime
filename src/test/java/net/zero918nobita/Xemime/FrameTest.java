package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Address;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.entity.Str;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.interpreter.Frame;
import net.zero918nobita.Xemime.interpreter.Main;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * net.zero918nobita.Xemime.interpreter.Frame のテストクラスです。
 * @author Kodai Matsumoto
 */

public class FrameTest {
    /** 改行コード */
    private String br = System.lineSeparator();

    @Test
    public void test() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Main.exec(
                "let variable = \"global\";\n" +
                        "{\n" +
                        "    let variable = \"local\";\n" +
                        "    println(if(variable == \"local\", T, NIL));\n" +
                        "};\n" +
                        "println(if(variable == \"global\", T, NIL));"
        );
        assertThat(out.toString(), is("T" + br + "T" + br));
    }

    @Test
    public void testGetValueOfSymbol() throws Exception {
        Frame frame = new Frame();
        // 未宣言のシンボル unknown の値を取得しようとすると null が返ることを確認する
        assertThat(frame.getValueOfSymbol(Symbol.intern(0, "unknown")), nullValue());
    }

    @Test
    public void testGetAddressOfSymbol() throws Exception {
        Frame frame = new Frame();
        frame.loadLocalFrame(new Handler(0));
        Address address = Main.register(new Str(0, "value"));
        frame.defAddress(Symbol.intern(0, "variable"), address);
        assertThat(frame.getAddressOfSymbol(Symbol.intern(0, "variable")), is(address));
    }
}
