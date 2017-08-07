package net.zero918nobita.Xemime;

import java.io.IOException;
import java.io.Reader;

/**
 * 字句解析器が使用するReader
 * @author Kodai Matsumoto
 */

class LexerReader {
    private String str;
    private int progress = -1;

    LexerReader(String s) {
        str = s;
    }

    int read() throws IOException {
        progress += 1;
        return str.charAt(progress);
    }

    void unread() {
        progress--;
    }

    void unread(int n) {
        progress -= n;
    }
}
