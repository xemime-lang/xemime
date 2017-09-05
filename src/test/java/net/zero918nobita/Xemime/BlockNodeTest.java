package net.zero918nobita.Xemime;

import net.zero918nobita.Xemime.ast.*;
import net.zero918nobita.Xemime.entity.Int;
import net.zero918nobita.Xemime.lexer.TokenType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * net.zero918nobita.Xemime.ast.BlockNode のテストクラスです。
 */

public class BlockNodeTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testRun() throws Exception {
        ArrayList<Node> list = new ArrayList<Node>(){{
            add(new ExprNode(0, TokenType.ADD, new Int(0, 1), new Int(0, 2)));
        }};
        BlockNode block = new BlockNode(0, list);
        assertThat(block.run(), is(new Int(0, 3)));
    }

    @Test
    public void testRun2() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("0: ブロックの戻り値が記述されていません");
        BlockNode block = new BlockNode(0, new ArrayList<>());
        block.run();
    }
}
