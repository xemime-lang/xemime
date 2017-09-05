package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Bool;
import net.zero918nobita.Xemime.ast.NotNode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.ast.NotNode のテストクラスです。
 * @author Kodai Matsumoto
 */

public class NotNodeTest {
    @Test
    public void testRun() throws Exception {
        NotNode t = new NotNode(0, Bool.T);
        NotNode nil = new NotNode(0, Bool.Nil);
        assertThat(t.run(), is(Bool.Nil));
        assertThat(nil.run(), is(Bool.T));
    }
}
