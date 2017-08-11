package net.zero918nobita.Xemime;

import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class FrameTest {
    @Test
    public void test() throws Exception {
        // グローバルの文字列型変数 variable を宣言し、"global" で初期化する
        Main.defValue(new X_Symbol("variable"), new X_String("global"));
        // ローカル変数のフレームを追加する
        Main.loadLocalFrame(new HashMap<>());
        // ローカルの文字列型変数 variable を宣言し、"local" で初期化する
        Main.defValue(new X_Symbol("variable"), new X_String("local"));
        // フレームの階層数が 1 であることを確認する
        assertThat(Main.frame.numberOfLayers(), is(1));
        // シンボル variable の参照先がローカル変数であることを確認する
        assertThat(Main.getValueOfSymbol(new X_Symbol("variable")), is(new X_String("local")));
        // フレームを破棄する
        Main.unloadLocalFrame();
        // シンボル variable の参照先がグローバル変数であることを確認する
        assertThat(Main.getValueOfSymbol(new X_Symbol("variable")), is(new X_String("global")));
        // フレームの階層数が 0 であることを確認する
        assertThat(Main.frame.numberOfLayers(), is(0));
    }

    @Test
    public void testGetValueOfSymbol() {
        Frame frame = new Frame();
        assertThat(frame.getValueOfSymbol(new X_Symbol("unknown"), new TreeMap<>()), nullValue());
    }
}
