package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.FatalException;
import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Int;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * net.zero918nobita.Xemime.entity.DoubleType のテストクラスです。
 * @author Kodai Matsumoto
 */

public class DoubleTest {
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
    }

    @Test
    public void testSub() throws FatalException {
        assertThat(new Double(0.2).sub(new Double(0.1)).toString(), is("0.1"));
    }

    @Test
    public void testMultiply() throws FatalException {
        assertThat(new Double(0.2).multiply(new Double(0.3)).toString(), is("0.06"));
    }

    @Test
    public void testDivide() throws FatalException {
        assertThat(new Double(0.2).divide(new Double(0.1)).toString(), is("2.0"));
    }
}
