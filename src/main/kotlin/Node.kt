package net.zero918nobita.xemime.ast

import net.zero918nobita.xemime.NodeType
import net.zero918nobita.xemime.Recognizable
import net.zero918nobita.xemime.entity.Bool
import java.util.ArrayList
import java.util.LinkedHashMap

open class Node(var location: Int) : Recognizable {
  @Throws(Exception::class)
  open fun run() = this

  @Throws(FatalException::class)
  open fun add(location: Int, rhs: Node): Node {
    throw FatalException(location, 98)
  }

  @Throws(FatalException::class)
  fun add(rhs: Node) = add(0, rhs)

  @Throws(FatalException::class)
  open fun sub(location: Int, rhs: Node): Node {
    throw FatalException(location, 99)
  }

  @Throws(FatalException::class)
  fun sub(rhs: Node) = add(0, rhs)

  @Throws(FatalException::class)
  open fun multiply(location: Int, rhs: Node): Node {
    throw FatalException(location, 100)
  }

  @Throws(FatalException::class)
  fun multiply(rhs: Node) = multiply(0, rhs)

  @Throws(FatalException::class)
  open fun divide(location: Int, rhs: Node): Node {
    throw FatalException(location, 101)
  }

  @Throws(FatalException::class)
  fun divide(rhs: Node) = divide(0, rhs)

  @Throws(FatalException::class)
  open fun mod(location: Int, rhs: Node): Node {
    throw FatalException(location, 140)
  }

  @Throws(FatalException::class)
  open fun less(location: Int, rhs: Node): Bool {
    throw FatalException(location, 102)
  }

  @Throws(FatalException::class)
  fun less(rhs: Node) = less(0, rhs)

  @Throws(FatalException::class)
  open fun le(location: Int, rhs: Node): Bool {
    throw FatalException(location, 103)
  }

  @Throws(FatalException::class)
  fun le(rhs: Node) = le(0, rhs)

  @Throws(FatalException::class)
  open fun greater(location: Int, rhs: Node): Bool {
    throw FatalException(location, 104)
  }

  @Throws(FatalException::class)
  fun greater(rhs: Node) = greater(0, rhs)

  @Throws(FatalException::class)
  open fun ge(location: Int, rhs: Node): Bool {
    throw FatalException(location, 105)
  }

  @Throws(FatalException::class)
  fun ge(rhs: Node) = ge(0, rhs)

  @Throws(FatalException::class)
  open fun and(location: Int, rhs: Node): Bool {
    throw FatalException(location, 114)
  }

  fun and(rhs: Node) = and(0, rhs)

  @Throws(FatalException::class)
  open fun or(location: Int, rhs: Node): Bool {
    throw FatalException(location, 115)
  }

  fun or(rhs: Node) = or(0, rhs)

  @Throws(FatalException::class)
  open fun xor(location: Int, rhs: Node): Bool {
    // `^` 演算子は使用できません。
    throw FatalException(location, 116)
  }

  @Throws(FatalException::class)
  fun xor(rhs: Node): Bool {
    return xor(0, rhs)
  }

  @Throws(Exception::class)
  open fun message(location: Int, symbol: Symbol): Node {
    throw Exception(location.toString() + ": `" + toString() + "` にフィールドは設定できません")
  }

  @Throws(Exception::class)
  open fun message(location: Int, symbol: Symbol, params: LinkedHashMap<Symbol, Node>): Node {
    throw Exception(location.toString() + ": `" + toString() + "` にメソッドは設定できません")
  }

  @Throws(Exception::class)
  open fun message(location: Int, symbol: Symbol, params: ArrayList<Node>): Node {
    throw Exception(location.toString() + ": `" + toString() + "` にメソッドを設定できません")
  }

  override fun recognize() = NodeType.NODE

  override fun `is`(comparison: NodeType) = comparison == recognize()
}
