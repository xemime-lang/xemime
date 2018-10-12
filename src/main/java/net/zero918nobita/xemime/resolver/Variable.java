package net.zero918nobita.xemime.resolver;

import net.zero918nobita.xemime.type.Type;

/**
 * 意味解析中に使用する、名前と型を格納するものです。
 * @author Kodai Matsumoto
 */

class Variable {
    private Type type;

    Variable(Type type) {
        this.type = type;
    }

    Type getType() {
        return type;
    }
}
