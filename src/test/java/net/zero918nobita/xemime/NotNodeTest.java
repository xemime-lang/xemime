package net.zero918nobita.xemime;

import net.zero918nobita.xemime.entity.Bool;
import net.zero918nobita.xemime.ast.NotNode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.xemime.ast.NotNode のテストクラスです。
 * @author Kodai Matsumoto
 */

public class NotNodeTest {
    @Test
    public void testRun() throws Exception {
        NotNode t = new NotNode(0, Bool.getT());
        NotNode nil = new NotNode(0, Bool.getNil());
        assertThat(t.run(), is(Bool.getNil()));
        assertThat(nil.run(), is(Bool.getT()));
    }
}
