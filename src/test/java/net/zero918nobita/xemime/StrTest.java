package net.zero918nobita.xemime;

import net.zero918nobita.xemime.ast.FatalException;
import net.zero918nobita.xemime.entity.Int;
import net.zero918nobita.xemime.entity.Str;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.xemime.entity.Str のテストクラスです。
 * @author Kodai Matsumoto
 */

public class StrTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testAdd() throws Exception {
        assertThat(new Str("hello, ").add(new Str("world!")),
                is(new Str("hello, world!")));
    }

    @Test
    public void testAdd2() throws FatalException {
        expectedException.expect(FatalException.class);
        expectedException.expectMessage("0: インタプリタ内部の深刻なエラーが発生しました。 [128]");
        new Str("foo").add(new Int(2));
    }
}
