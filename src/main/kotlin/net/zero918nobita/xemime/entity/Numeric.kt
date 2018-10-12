package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.ast.Node
import kotlin.Int

abstract class Numeric(location: Int): Node(location) {
  lateinit var value: Number
}
