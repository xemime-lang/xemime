package net.zero918nobita.Xemime;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.Xemime.X_Int のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_IntTest {
    @Test
    public void testAbs() throws Exception {
        X_Int i = (X_Int) (new X_Minus(0, new X_Int(0, 256))).run();
        X_Int abs = (X_Int) i.message(0, X_Symbol.intern(0, "abs"), new ArrayList<>());
        assertThat(i.getValue(), is(-256));
        assertThat(abs.getValue(), is(256));
    }

    @Test
    public void testToS() throws Exception {
        X_String str = (X_String) new X_Int(0, 2).message(0, X_Symbol.intern(0, "to_s"), new ArrayList<>());
        assertThat(str.toString(), is("2"));
    }
}
