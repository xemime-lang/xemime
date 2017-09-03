package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.X_Bool;
import net.zero918nobita.Xemime.ast.X_Not;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.ast.X_Not のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_NotTest {
    @Test
    public void testRun() throws Exception {
        X_Not t = new X_Not(0, X_Bool.T);
        X_Not nil = new X_Not(0, X_Bool.Nil);
        assertThat(t.run(), is(X_Bool.Nil));
        assertThat(nil.run(), is(X_Bool.T));
    }
}
