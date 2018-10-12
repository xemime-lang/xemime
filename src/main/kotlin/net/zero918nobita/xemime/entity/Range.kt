package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.ast.Node
import kotlin.Int

class Range(location: Int, val left: Int, val right: Int, private val isMaxElement: Boolean) : Node(location) {

  override fun toString() = "$left${if (isMaxElement) ".." else "..." }$right"
}
