package net.zero918nobita.Xemime;

import java.io.IOException;
import java.io.Reader;

/**
 * 字句解析器が使用するReader
 * @author Kodai Matsumoto
 */

class LexerReader {
    private Reader reader;
    private boolean unget_p = false;
    private int ch;

    LexerReader(Reader r) {
        reader = r;
    }

    int read() throws IOException {
        if (unget_p) {
            unget_p = false;
        } else {
            ch = reader.read();
        }
        return ch;
    }

    void unread() {
        unget_p = true;
    }
}
