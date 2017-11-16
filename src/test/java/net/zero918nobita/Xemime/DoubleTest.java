package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.entity.Str;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * net.zero918nobita.Xemime.entity.DoubleType のテストクラスです。
 * @author Kodai Matsumoto
 */

public class DoubleTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testToString() {
        assertThat(new Double(0.2).toString(), is("0.2"));
    }

    @Test
    public void testEquals() {
        assertTrue(new Double(0.2).equals(new Double(0.20)));
        assertTrue(new Double(2.0).equals(new Int(2)));
        assertFalse(new Double(0.2).equals(new Double(2)));
    }

    @Test
    public void testAdd() throws FatalException {
        assertThat(new Double(0.2).add(new Double(0.3)).toString(), is("0.5"));
        assertThat(new Double(0.2).add(new Int(2)).toString(), is("2.2"));
    }

    @Test
    public void testAdd2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [120]");
        new Double(0.2).add(new Str("hello"));
    }

    @Test
    public void testSub() throws FatalException {
        assertThat(new Double(0.2).sub(new Double(0.1)).toString(), is("0.1"));
        assertThat(new Double(0.2).sub(new Int(1)).toString(), is("-0.8"));
    }

    @Test
    public void testSub2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [121]");
        new Double(0.2).sub(new Str("hello"));
    }

    @Test
    public void testMultiply() throws FatalException {
        assertThat(new Double(0.2).multiply(new Double(0.3)).toString(), is("0.06"));
        assertThat(new Double(0.2).multiply(new Int(2)).toString(), is("0.4"));
    }

    @Test
    public void testDivide() throws FatalException {
        assertThat(new Double(0.2).divide(new Double(0.1)).toString(), is("2.0"));
        assertThat(new Double(0.2).divide(new Int(2)).toString(), is("0.1"));
    }

    @Test
    public void testLess() throws FatalException {
        assertThat(new Double(0.2).less(new Double(0.3)).toString(), is("T"));
        assertThat(new Double(0.3).less(new Double(0.2)).toString(), is("NIL"));
        assertThat(new Double(0.2).less(new Double(0.2)).toString(), is("NIL"));
    }

    @Test
    public void testLe() throws FatalException {
        assertThat(new Double(0.2).le(new Double(0.3)).toString(), is("T"));
        assertThat(new Double(0.3).le(new Double(0.2)).toString(), is("NIL"));
        assertThat(new Double(0.2).le(new Double(0.2)).toString(), is("T"));
    }

    @Test
    public void testGreater() throws FatalException {
        assertThat(new Double(0.2).greater(new Double(0.1)).toString(), is("T"));
        assertThat(new Double(0.1).greater(new Double(0.2)).toString(), is("NIL"));
        assertThat(new Double(0.2).greater(new Double(0.2)).toString(), is("NIL"));
    }

    @Test
    public void testGe() throws FatalException {
        assertThat(new Double(0.2).ge(new Double(0.1)).toString(), is("T"));
        assertThat(new Double(0.1).ge(new Double(0.2)).toString(), is("NIL"));
        assertThat(new Double(0.2).ge(new Double(0.2)).toString(), is("T"));
    }
}
