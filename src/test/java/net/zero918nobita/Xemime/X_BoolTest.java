package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.X_Bool;
import net.zero918nobita.Xemime.ast.X_String;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.Xemime.ast.X_Bool のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_BoolTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testToString() {
        assertThat(X_Bool.T.toString(), is("T"));
        assertThat(X_Bool.Nil.toString(), is("NIL"));
    }

    @Test
    public void testEquals() {
        assertThat(X_Bool.T.equals(new X_Bool(0, true)), is(true));
        assertThat(X_Bool.T.equals(new X_Bool(0, false)), is(false));
        assertThat(X_Bool.Nil.equals(new X_Bool(0, false)), is(true));
        assertThat(X_Bool.Nil.equals(new X_Bool(0, true)), is(false));
    }

    @Test
    public void testAdd() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の加算はできません");
        X_Bool.T.add(0, X_Bool.T);
    }

    @Test
    public void testSub() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の減算はできません");
        X_Bool.T.sub(0, X_Bool.T);
    }

    @Test
    public void testMultiply() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の乗算はできません");
        X_Bool.T.multiply(0, X_Bool.T);
    }

    @Test
    public void testDivide() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値の除算はできません");
        X_Bool.T.divide(0, X_Bool.T);
    }

    @Test
    public void testLess() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `<` 演算子は使用できません");
        X_Bool.T.less(0, X_Bool.Nil);
    }

    @Test
    public void testLe() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `<=` 演算子は使用できません");
        X_Bool.T.le(0, X_Bool.Nil);
    }

    @Test
    public void testGreater() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `>` 演算子は使用できません");
        X_Bool.T.greater(0, X_Bool.Nil);
    }

    @Test
    public void testGe() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 真偽値に `>=` 演算子は使用できません");
        X_Bool.T.ge(0, X_Bool.Nil);
    }

    @Test
    public void testAnd() throws Exception {
        assertThat(X_Bool.T.and(0, X_Bool.T), is(X_Bool.T));
        assertThat(X_Bool.T.and(0, X_Bool.Nil), is(X_Bool.Nil));
        assertThat(X_Bool.Nil.and(0, X_Bool.Nil), is(X_Bool.Nil));
        assertThat(X_Bool.Nil.and(0, X_Bool.T), is(X_Bool.Nil));
    }

    @Test
    public void testAnd2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: `&&` 演算子の右辺が真偽値ではありません");
        X_Bool.T.and(0, new X_String(0, "foo"));
    }

    @Test
    public void testOr() throws Exception {
        assertThat(X_Bool.T.or(0, X_Bool.T), is(X_Bool.T));
        assertThat(X_Bool.T.or(0, X_Bool.Nil), is(X_Bool.T));
        assertThat(X_Bool.Nil.or(0, X_Bool.T), is(X_Bool.T));
        assertThat(X_Bool.Nil.or(0, X_Bool.Nil), is(X_Bool.Nil));
    }

    @Test
    public void testOr2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: `||` 演算子の右辺が真偽値ではありません");
        X_Bool.T.or(0, new X_String(0, "foo"));
    }

    @Test
    public void testXor() throws Exception {
        assertThat(X_Bool.T.xor(0, X_Bool.T), is(X_Bool.Nil));
        assertThat(X_Bool.T.xor(0, X_Bool.Nil), is(X_Bool.T));
        assertThat(X_Bool.Nil.xor(0, X_Bool.T), is(X_Bool.T));
        assertThat(X_Bool.Nil.xor(0, X_Bool.Nil), is(X_Bool.Nil));
    }

    @Test
    public void testXor2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage(": `^` 演算子の右辺が真偽値ではありません");
        X_Bool.T.xor(0, new X_String(0, "foo"));
    }
}
