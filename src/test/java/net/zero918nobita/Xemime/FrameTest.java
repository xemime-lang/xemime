package net.zero918nobita.Xemime;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class FrameTest {
    @Test
    public void testNumberOfLayers() throws Exception {
        Main.loadLocalFrame(new HashMap<>());
        Main.defValue(new X_Symbol("sample"), new X_String("value"));
        assertThat(Main.frame.numberOfLayers(), is(1));
    }
}
