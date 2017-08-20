package net.zero918nobita.Xemime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.X_Minus のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_MinusTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testRun() throws Exception {
        X_Minus minus = new X_Minus(0, new X_Double(0, 0.2));
        assertThat(minus.run(), is(new X_Double(0, -0.2)));
    }

    @Test
    public void testRun2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: 数値以外のものには単項演算子を適用できません");
        X_Minus minus = new X_Minus(0, new X_String(0, "foo"));
        minus.run();
    }
}
