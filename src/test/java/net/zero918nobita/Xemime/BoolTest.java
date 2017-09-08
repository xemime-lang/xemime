package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.entity.Str;
import net.zero918nobita.Xemime.interpreter.Main;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.Xemime.entity.Bool のテストクラスです。
 * @author Kodai Matsumoto
 */

public class BoolTest {
    private String br = System.lineSeparator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Main.exec(
                "let variable = T;\n" +
                        "println(variable);\n" +
                        "println(!variable);\n"
        );
        assertThat(out.toString(), is("T" + br + "NIL" + br));
    }

    @Test
    public void testToString() {
        assertThat(Bool.T.toString(), is("T"));
        assertThat(Bool.Nil.toString(), is("NIL"));
    }

    @Test
    public void testEquals() {
        assertThat(Bool.T.equals(new Bool(0, true)), is(true));
        assertThat(Bool.T.equals(new Bool(0, false)), is(false));
        assertThat(Bool.Nil.equals(new Bool(0, false)), is(true));
        assertThat(Bool.Nil.equals(new Bool(0, true)), is(false));
    }

    @Test
    public void testAdd() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の加算はできません");
        Bool.T.add(0, Bool.T);
    }

    @Test
    public void testSub() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の減算はできません");
        Bool.T.sub(0, Bool.T);
    }

    @Test
    public void testMultiply() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の乗算はできません");
        Bool.T.multiply(0, Bool.T);
    }

    @Test
    public void testDivide() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の除算はできません");
        Bool.T.divide(0, Bool.T);
    }

    @Test
    public void testLess() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `<` 演算子は使用できません");
        Bool.T.less(0, Bool.Nil);
    }

    @Test
    public void testLe() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `<=` 演算子は使用できません");
        Bool.T.le(0, Bool.Nil);
    }

    @Test
    public void testGreater() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `>` 演算子は使用できません");
        Bool.T.greater(0, Bool.Nil);
    }

    @Test
    public void testGe() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `>=` 演算子は使用できません");
        Bool.T.ge(0, Bool.Nil);
    }

    @Test
    public void testAnd() throws Exception {
        assertThat(Bool.T.and(0, Bool.T), is(Bool.T));
        assertThat(Bool.T.and(0, Bool.Nil), is(Bool.Nil));
        assertThat(Bool.Nil.and(0, Bool.Nil), is(Bool.Nil));
        assertThat(Bool.Nil.and(0, Bool.T), is(Bool.Nil));
    }

    @Test
    public void testAnd2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: `&&` 演算子の右辺が真偽値ではありません");
        Bool.T.and(0, new Str(0, "foo"));
    }

    @Test
    public void testOr() throws Exception {
        assertThat(Bool.T.or(0, Bool.T), is(Bool.T));
        assertThat(Bool.T.or(0, Bool.Nil), is(Bool.T));
        assertThat(Bool.Nil.or(0, Bool.T), is(Bool.T));
        assertThat(Bool.Nil.or(0, Bool.Nil), is(Bool.Nil));
    }

    @Test
    public void testOr2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: `||` 演算子の右辺が真偽値ではありません");
        Bool.T.or(0, new Str(0, "foo"));
    }

    @Test
    public void testXor() throws Exception {
        assertThat(Bool.T.xor(0, Bool.T), is(Bool.Nil));
        assertThat(Bool.T.xor(0, Bool.Nil), is(Bool.T));
        assertThat(Bool.Nil.xor(0, Bool.T), is(Bool.T));
        assertThat(Bool.Nil.xor(0, Bool.Nil), is(Bool.Nil));
    }

    @Test
    public void testXor2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage(": `^` 演算子の右辺が真偽値ではありません");
        Bool.T.xor(0, new Str(0, "foo"));
    }
}
