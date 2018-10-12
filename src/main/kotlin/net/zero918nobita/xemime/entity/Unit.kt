package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.ast.Node
import kotlin.Int

class Unit(location: Int, private val body: Node?) : Node(location) {
  @Throws(Exception::class)
  override fun run(): Node {
    body?.run()
    return this
  }

  override fun toString() = "unit"
}
