package net.zero918nobita.Xemime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.X_String のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_StringTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testAdd() throws Exception {
        assertThat(new X_String(0, "hello, ").add(0, new X_String(0, "world!")),
                is(new X_String(0, "hello, world!")));
    }

    @Test
    public void testAdd2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: String型オブジェクトに他の型のオブジェクトを加算することはできません");
        new X_String(0, "foo").add(0, new X_Int(0, 2));
    }

    @Test
    public void testLength() throws Exception {
        assertThat(new X_String(0, "foo").message(0, X_Symbol.intern(0, "length"), new ArrayList<>()),
                is(new X_Int(0, 3)));
    }
}
