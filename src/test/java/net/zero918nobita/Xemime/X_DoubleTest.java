package net.zero918nobita.Xemime;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * net.zero918nobita.Xemime.X_Double のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_DoubleTest {
    @Test
    public void testToString() {
        assertThat(new X_Double(0, 0.2).toString(), is("0.2"));
    }

    @Test
    public void testEquals() {
        assertTrue(new X_Double(0, 0.2).equals(new X_Double(0, 0.20)));
        assertTrue(new X_Double(0, 2.0).equals(new X_Int(0, 2)));
        assertFalse(new X_Double(0, 0.2).equals(new X_Double(0, 2)));
    }
}
