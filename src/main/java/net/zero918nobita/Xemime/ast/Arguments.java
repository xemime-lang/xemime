package net.zero918nobita.Xemime.ast;

import java.util.LinkedHashMap;

public class Arguments {
    private boolean hasNamedArgs = false;
    private LinkedHashMap<Symbol, Node> params;

    public Arguments(LinkedHashMap<Symbol, Node> params, boolean hasNamedArgs) {
        this.params = params;
        this.hasNamedArgs = hasNamedArgs;
    }

    public boolean namedArgs() {
        return hasNamedArgs;
    }

    public LinkedHashMap<Symbol, Node> getParams() {
        return params;
    }
}
