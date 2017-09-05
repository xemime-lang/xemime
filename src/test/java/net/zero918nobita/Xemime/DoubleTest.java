package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Double;
import net.zero918nobita.Xemime.entity.Int;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * net.zero918nobita.Xemime.entity.Double のテストクラスです。
 * @author Kodai Matsumoto
 */

public class DoubleTest {
    @Test
    public void testToString() {
        assertThat(new Double(0, 0.2).toString(), is("0.2"));
    }

    @Test
    public void testEquals() {
        assertTrue(new Double(0, 0.2).equals(new Double(0, 0.20)));
        assertTrue(new Double(0, 2.0).equals(new Int(0, 2)));
        assertFalse(new Double(0, 0.2).equals(new Double(0, 2)));
    }
}
