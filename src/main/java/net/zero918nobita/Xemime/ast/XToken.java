package net.zero918nobita.Xemime.ast;

import net.zero918nobita.Xemime.parser.ParserConstants;
import net.zero918nobita.Xemime.parser.Token;

import java.util.ArrayList;
import java.util.List;

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

    protected Token specialTokenHead(Token firstSpecial) {
        Token s = firstSpecial;
        while (s.specialToken != null) s = s.specialToken;
        return s;
    }

    protected List<XToken> buildTokenList(Token first, boolean rejectFirstSpecials) {
        List<XToken> result = new ArrayList<>();
        boolean rejectSpecials = rejectFirstSpecials;
        for (Token t = first; t != null; t = t.next) {
            if (t.specialToken != null && !rejectSpecials) {
                Token s = specialTokenHead(t.specialToken);
                for (; s != null; s = s.next) result.add(new XToken(s));
            }
            result.add(new XToken(t));
            rejectSpecials = false;
        }
        return result;
    }

    public List<XToken> tokensWithoutFirstSpecials() {
        return buildTokenList(token, true);
    }

    public String includedLine() {
        StringBuffer buf = new StringBuffer();
        for (XToken t : tokensWithoutFirstSpecials()) {
            int idx = t.image().indexOf('\n');
            if (idx > 0) {
                buf.append(t.image().substring(0, idx));
                break;
            }
            buf.append(t.image());
        }
        return buf.toString();
    }
}
