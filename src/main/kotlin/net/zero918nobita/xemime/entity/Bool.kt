package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.NodeType
import net.zero918nobita.xemime.ast.FatalException
import net.zero918nobita.xemime.ast.Node
import kotlin.Int

class Bool(location: Int, private val bool: Boolean) : Node(location) {
  companion object {
    @JvmStatic
    val T = Bool(true)
    @JvmStatic
    val Nil = Bool(false)
  }

  constructor(boolean: Boolean) : this(0, boolean)

  override fun recognize() = NodeType.BOOL

  override fun toString() = if (bool) "T" else "NIL"

  override fun equals(other: Any?) = other is Bool && other.bool == bool

  override fun hashCode() = bool.hashCode()

  @Throws(FatalException::class)
  override fun and(location: Int, rhs: Node): Bool {
    if (rhs is Bool) return if (bool && rhs.bool) Bool.T else Bool.Nil
    throw FatalException(location, 117)
  }

  @Throws(FatalException::class)
  override fun or(location: Int, rhs: Node): Bool {
    if (rhs is Bool) return if (bool || rhs.bool) Bool.T else Bool.Nil
    throw FatalException(location, 118)
  }

  @Throws(FatalException::class)
  override fun xor(location: Int, rhs: Node): Bool {
    if (rhs is Bool) return if (bool xor rhs.bool) Bool.T else Bool.Nil
    throw FatalException(location, 119)
  }
}
