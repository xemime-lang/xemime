package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.entity.Address;
import net.zero918nobita.Xemime.entity.Handler;
import net.zero918nobita.Xemime.entity.Str;
import net.zero918nobita.Xemime.ast.Symbol;
import net.zero918nobita.Xemime.interpreter.Frame;
import net.zero918nobita.Xemime.interpreter.Main;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * net.zero918nobita.Xemime.interpreter.Frame のテストクラスです。
 * @author Kodai Matsumoto
 */

public class FrameTest {
    @Test
    public void test() throws Exception {
        // グローバルの文字列型変数 variable を宣言し、"global" で初期化する
        Main.defValue(Symbol.intern(0, "variable"), new Str(0, "global"));
        // ローカル変数のフレームを追加する
        Main.loadLocalFrame(new Handler(0));
        // ローカルの文字列型変数 variable を宣言し、"local" で初期化する
        Main.defValue(Symbol.intern(0, "variable"), new Str(0, "local"));
        // フレームの階層数が 1 であることを確認する
        assertThat(Main.frame.numberOfLayers(), is(1));
        // シンボル variable の参照先がローカル変数であることを確認する
        assertThat(Main.getValueOfSymbol(Symbol.intern(0, "variable")), is(new Str(0, "local")));
        // フレームを破棄する
        Main.unloadLocalFrame();
        // シンボル variable の参照先がグローバル変数であることを確認する
        assertThat(Main.getValueOfSymbol(Symbol.intern(0, "variable")), is(new Str(0, "global")));
        // フレームの階層数が 0 であることを確認する
        assertThat(Main.frame.numberOfLayers(), is(0));
    }

    @Test
    public void testGetValueOfSymbol() throws Exception {
        Frame frame = new Frame();
        // 未宣言のシンボル unknown の値を取得しようとすると null が返ることを確認する
        assertThat(frame.getValueOfSymbol(Symbol.intern(0, "unknown")), nullValue());
    }

    @Test
    public void testGetAddressOfSymbol() throws Exception {
        Frame frame = new Frame();
        frame.loadLocalFrame(new Handler(0));
        Address address = Main.register(new Str(0, "value"));
        frame.defAddress(Symbol.intern(0, "variable"), address);
        assertThat(frame.getAddressOfSymbol(Symbol.intern(0, "variable")), is(address));
    }
}
