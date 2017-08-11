package net.zero918nobita.Xemime;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class FrameTest {
    @Test
    public void test() throws Exception {
        Main.defValue(new X_Symbol("variable"), new X_String("global"));
        Main.loadLocalFrame(new HashMap<>());
        Main.defValue(new X_Symbol("variable"), new X_String("local"));
        assertThat(Main.frame.numberOfLayers(), is(1));
        assertThat(Main.getValueOfSymbol(new X_Symbol("variable")), is(new X_String("local")));
        Main.unloadLocalFrame();
        assertThat(Main.getValueOfSymbol(new X_Symbol("variable")), is(new X_String("global")));
    }
}
