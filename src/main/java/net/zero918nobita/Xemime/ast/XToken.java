package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.parser.ParserConstants;
import net.zero918nobita.Xemime.parser.Token;

public class XToken {
    protected Token token;
    protected boolean isSpecial;

    public XToken(Token token, boolean isSpecial) {
        this.token = token;
        this.isSpecial = isSpecial;
    }

    public XToken(Token token) {
        this(token, false);
    }

    @Override
    public String toString() {
        return token.image;
    }

    public boolean isSpecial() {
        return this.isSpecial;
    }

    public int kindID() {
        return token.kind;
    }

    public String kindName() {
        return ParserConstants.tokenImage[token.kind];
    }

    public int lineno() {
        return token.beginLine;
    }

    public int column() {
        return token.beginColumn;
    }

    public String image() {
        return token.image;
    }
}
