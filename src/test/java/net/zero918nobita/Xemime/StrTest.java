package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.entity.Str;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.entity.Str のテストクラスです。
 * @author Kodai Matsumoto
 */

public class StrTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testAdd() throws Exception {
        assertThat(new Str(0, "hello, ").add(0, new Str(0, "world!")),
                is(new Str(0, "hello, world!")));
    }

    @Test
    public void testAdd2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: String型オブジェクトに他の型のオブジェクトを加算することはできません");
        new Str(0, "foo").add(0, new Int(0, 2));
    }
}
