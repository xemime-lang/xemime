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
        assertThat(Bool.getT().toString(), is("T"));
        assertThat(Bool.getNil().toString(), is("NIL"));
    }

    @Test
    public void testEquals() {
        assertThat(Bool.getT().equals(new Bool(true)), is(true));
        assertThat(Bool.getT().equals(new Bool(false)), is(false));
        assertThat(Bool.getNil().equals(new Bool(false)), is(true));
        assertThat(Bool.getNil().equals(new Bool(true)), is(false));
    }

    @Test
    public void testAdd() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [98]");
        Bool.getT().add(Bool.getT());
    }

    @Test
    public void testSub() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [99]");
        Bool.getT().sub(Bool.getT());
    }

    @Test
    public void testMultiply() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [100]");
        Bool.getT().multiply(Bool.getT());
    }

    @Test
    public void testDivide() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [101]");
        Bool.getT().divide(Bool.getT());
    }

    @Test
    public void testLess() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [102]");
        Bool.getT().less(Bool.getNil());
    }

    @Test
    public void testLe() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [103]");
        Bool.getT().le(Bool.getNil());
    }

    @Test
    public void testGreater() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [104]");
        Bool.getT().greater(Bool.getNil());
    }

    @Test
    public void testGe() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [105]");
        Bool.getT().ge(Bool.getNil());
    }

    @Test
    public void testAnd() throws Exception {
        assertThat(Bool.getT().and(Bool.getT()), is(Bool.getT()));
        assertThat(Bool.getT().and(Bool.getNil()), is(Bool.getNil()));
        assertThat(Bool.getNil().and(Bool.getNil()), is(Bool.getNil()));
        assertThat(Bool.getNil().and(Bool.getT()), is(Bool.getNil()));
    }

    @Test
    public void testAnd2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [117]");
        Bool.getT().and(new Str("foo"));
    }

    @Test
    public void testOr() throws Exception {
        assertThat(Bool.getT().or(Bool.getT()), is(Bool.getT()));
        assertThat(Bool.getT().or(Bool.getNil()), is(Bool.getT()));
        assertThat(Bool.getNil().or(Bool.getT()), is(Bool.getT()));
        assertThat(Bool.getNil().or(Bool.getNil()), is(Bool.getNil()));
    }

    @Test
    public void testOr2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [118]");
        Bool.getT().or(new Str("foo"));
    }

    @Test
    public void testXor() throws Exception {
        assertThat(Bool.getT().xor(Bool.getT()), is(Bool.getNil()));
        assertThat(Bool.getT().xor(Bool.getNil()), is(Bool.getT()));
        assertThat(Bool.getNil().xor(Bool.getT()), is(Bool.getT()));
        assertThat(Bool.getNil().xor(Bool.getNil()), is(Bool.getNil()));
    }

    @Test
    public void testXor2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [119]");
        Bool.getT().xor(new Str("foo"));
    }
}
