package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.ast.MinusNode;
import net.zero918nobita.Xemime.entity.Str;
import net.zero918nobita.Xemime.ast.Symbol;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * net.zero918nobita.Xemime.entity.Int のテストクラスです。
 * @author Kodai Matsumoto
 */

public class X_IntTest {
    @Test
    public void testAbs() throws Exception {
        Int i = (Int) (new MinusNode(0, new Int(0, 256))).run();
        Int abs = (Int) i.message(0, Symbol.intern(0, "abs"), new ArrayList<>());
        assertThat(i.getValue(), is(-256));
        assertThat(abs.getValue(), is(256));
    }

    @Test
    public void testToS() throws Exception {
        Str str = (Str) new Int(0, 2).message(0, Symbol.intern(0, "to_s"), new ArrayList<>());
        assertThat(str.toString(), is("2"));
    }
}
