package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.parser.Token;

public class Location {
    protected String sourceName;
    protected XToken token;

    public Location(String sourceName, XToken token) {
        this.sourceName = sourceName;
        this.token = token;
    }

    public Location(String sourceName, Token token) {
        this(sourceName, new XToken(token));
    }

    public String sourceName() {
        return sourceName;
    }

    public XToken token() {
        return token;
    }

    public int lineno() {
        return token.lineno();
    }

    public int column() {
        return token.column();
    }

    public String line() {
        return token.includedLine();
    }
}
