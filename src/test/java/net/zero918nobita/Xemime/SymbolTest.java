package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.interpreter.Main;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.Xemime.ast.Symbol のテストクラスです。
 * @author Kodai Matsumoto
 */

public class SymbolTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testEquals() {
        Symbol a = Symbol.intern(0, "A");
        Symbol b = Symbol.intern(0, "A");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void testRun() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("シンボル `unknown` は定義されていません");
        Symbol symbol = Symbol.intern(0, "unknown");
        symbol.run();
    }

    @Test
    public void testRun2() throws Exception {
        Main.defValue(Symbol.intern(0, "variable"), new Symbol(0, "value"));
        Symbol symbol = Symbol.intern(0, "variable");
        assertThat(symbol.run().toString(), is("value"));
    }

    @Test
    public void testToString() {
        Symbol symbol = Symbol.intern(0, "sample");
        assertThat(symbol.toString(), is("sample"));
    }

    @Test
    public void testGetName() {
        Symbol symbol = Symbol.intern(0, "sample2");
        assertThat(symbol.getName(), is("sample2"));
    }
}
