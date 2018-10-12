package net.zero918nobita.xemime;

import net.zero918nobita.xemime.ast.FatalException;
import net.zero918nobita.xemime.entity.Bool;
import net.zero918nobita.xemime.entity.Str;
import net.zero918nobita.xemime.interpreter.Main;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.xemime.entity.Bool のテストクラスです。
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
                "let variable = T\n" +
                        "println(variable)\n" +
                        "println(!variable)\n"
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
        assertThat(Bool.T.equals(new Bool(true)), is(true));
        assertThat(Bool.T.equals(new Bool(false)), is(false));
        assertThat(Bool.Nil.equals(new Bool(false)), is(true));
        assertThat(Bool.Nil.equals(new Bool(true)), is(false));
    }

    @Test
    public void testAdd() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [98]");
        Bool.T.add(Bool.T);
    }

    @Test
    public void testSub() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [99]");
        Bool.T.sub(Bool.T);
    }

    @Test
    public void testMultiply() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [100]");
        Bool.T.multiply(Bool.T);
    }

    @Test
    public void testDivide() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [101]");
        Bool.T.divide(Bool.T);
    }

    @Test
    public void testLess() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [102]");
        Bool.T.less(Bool.Nil);
    }

    @Test
    public void testLe() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [103]");
        Bool.T.le(Bool.Nil);
    }

    @Test
    public void testGreater() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [104]");
        Bool.T.greater(Bool.Nil);
    }

    @Test
    public void testGe() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [105]");
        Bool.T.ge(Bool.Nil);
    }

    @Test
    public void testAnd() throws Exception {
        assertThat(Bool.T.and(Bool.T), is(Bool.T));
        assertThat(Bool.T.and(Bool.Nil), is(Bool.Nil));
        assertThat(Bool.Nil.and(Bool.Nil), is(Bool.Nil));
        assertThat(Bool.Nil.and(Bool.T), is(Bool.Nil));
    }

    @Test
    public void testAnd2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [117]");
        Bool.T.and(new Str("foo"));
    }

    @Test
    public void testOr() throws Exception {
        assertThat(Bool.T.or(Bool.T), is(Bool.T));
        assertThat(Bool.T.or(Bool.Nil), is(Bool.T));
        assertThat(Bool.Nil.or(Bool.T), is(Bool.T));
        assertThat(Bool.Nil.or(Bool.Nil), is(Bool.Nil));
    }

    @Test
    public void testOr2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [118]");
        Bool.T.or(new Str("foo"));
    }

    @Test
    public void testXor() throws Exception {
        assertThat(Bool.T.xor(Bool.T), is(Bool.Nil));
        assertThat(Bool.T.xor(Bool.Nil), is(Bool.T));
        assertThat(Bool.Nil.xor(Bool.T), is(Bool.T));
        assertThat(Bool.Nil.xor(Bool.Nil), is(Bool.Nil));
    }

    @Test
    public void testXor2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [119]");
        Bool.T.xor(new Str("foo"));
    }
}
