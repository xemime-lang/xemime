package net.zero918nobita.Xemime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.Xemime.X_Symbol のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_SymbolTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testEquals() {
        X_Symbol a = X_Symbol.intern(0, "A");
        X_Symbol b = X_Symbol.intern(0, "A");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void testRun() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("シンボル `unknown` は定義されていません");
        X_Symbol symbol = X_Symbol.intern(0, "unknown");
        symbol.run();
    }

    @Test
    public void testRun2() throws Exception {
        Main.defValue(X_Symbol.intern(0, "variable"), new X_Symbol(0, "value"));
        X_Symbol symbol = X_Symbol.intern(0, "variable");
        assertThat(symbol.run().toString(), is("value"));
    }

    @Test
    public void testToString() {
        X_Symbol symbol = X_Symbol.intern(0, "sample");
        assertThat(symbol.toString(), is("sample"));
    }

    @Test
    public void testGetName() {
        X_Symbol symbol = X_Symbol.intern(0, "sample2");
        assertThat(symbol.getName(), is("sample2"));
    }
}
