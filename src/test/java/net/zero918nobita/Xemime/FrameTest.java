package net.zero918nobita.Xemime;

import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * net.zero918nobita.Xemime.Frame のテストクラスです。
 * @author Kodai Matsumoto
 */

public class FrameTest {
    @Test
    public void test() throws Exception {
        // グローバルの文字列型変数 variable を宣言し、"global" で初期化する
        Main.defValue(X_Symbol.intern(0, "variable"), new X_String(0, "global"));
        // ローカル変数のフレームを追加する
        Main.loadLocalFrame(new X_Handler(0));
        // ローカルの文字列型変数 variable を宣言し、"local" で初期化する
        Main.defValue(X_Symbol.intern(0, "variable"), new X_String(0, "local"));
        // フレームの階層数が 1 であることを確認する
        assertThat(Main.frame.numberOfLayers(), is(1));
        // シンボル variable の参照先がローカル変数であることを確認する
        assertThat(Main.getValueOfSymbol(X_Symbol.intern(0, "variable")), is(new X_String(0, "local")));
        // フレームを破棄する
        Main.unloadLocalFrame();
        // シンボル variable の参照先がグローバル変数であることを確認する
        assertThat(Main.getValueOfSymbol(X_Symbol.intern(0, "variable")), is(new X_String(0, "global")));
        // フレームの階層数が 0 であることを確認する
        assertThat(Main.frame.numberOfLayers(), is(0));
    }

    @Test
    public void testGetValueOfSymbol() throws Exception {
        Frame frame = new Frame();
        // 未宣言のシンボル unknown の値を取得しようとすると null が返ることを確認する
        assertThat(frame.getValueOfSymbol(X_Symbol.intern(0, "unknown")), nullValue());
    }

    @Test
    public void testGetAddressOfSymbol() throws Exception {
        Frame frame = new Frame();
        frame.loadLocalFrame(new X_Handler(0));
        X_Address address = Main.register(new X_String(0, "value"));
        frame.defAddress(X_Symbol.intern(0, "variable"), address);
        assertThat(frame.getAddressOfSymbol(X_Symbol.intern(0, "variable")), is(address));
    }
}
