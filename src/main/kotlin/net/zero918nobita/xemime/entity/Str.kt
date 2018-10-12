package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.NodeType
import net.zero918nobita.xemime.ast.FatalException
import net.zero918nobita.xemime.ast.Node
import kotlin.Int

class Str(location: Int, val str: String) : Node(location) {
  constructor(str: String) : this(0, str)

  override fun recognize() = NodeType.STR

  override fun toString() = str

  override fun equals(other: Any?) = other is Str && str == other.str

  override fun hashCode() = str.hashCode()

  @Throws(FatalException::class)
  override fun add(location: Int, rhs: Node): Node {
    if (rhs is Str) return Str(str + rhs.str)
    throw FatalException(location, 128)
  }
}
