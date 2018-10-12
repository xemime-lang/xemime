package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.NodeType
import net.zero918nobita.xemime.ast.FatalException
import net.zero918nobita.xemime.ast.Node
import kotlin.Int as KInt
import kotlin.Double as KDouble

class Double(location: KInt, val value: KDouble) : Numeric(location) {
  constructor(value: KDouble) : this(0, value)

  override fun recognize() = NodeType.DOUBLE

  override fun toString() = value.toString()

  override fun equals(other: Any?): Boolean {
    return if (other is Double) {
      other.value == value
    } else {
      (other is Int) && value == other.value.toDouble()
    }
  }

  override fun hashCode() = value.hashCode()

  @Throws(FatalException::class)
  override fun add(location: KInt, rhs: Node) =
      when (rhs) {
        is Int -> Double(value + rhs.value.toDouble())
        is Double -> Double(value + rhs.value)
        else -> throw FatalException(location, 106)
      }

  @Throws(FatalException::class)
  override fun sub(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> Double(value - rhs.value.toDouble())
        is Double -> Double(value - rhs.value)
        else -> throw FatalException(location, 107)
      }

  @Throws(FatalException::class)
  override fun multiply(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> Double(value * rhs.value.toDouble())
        is Double -> Double(value * rhs.value)
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun divide(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> Double(value / rhs.value.toDouble())
        is Double -> Double(value / rhs.value)
        else -> throw FatalException(location, 109)
      }

  @Throws(FatalException::class)
  override fun less(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value < rhs.value) Bool.T else Bool.Nil
        is Double -> if (value < rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun le(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value <= rhs.value) Bool.T else Bool.Nil
        is Double -> if (value <= rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun greater(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value > rhs.value) Bool.T else Bool.Nil
        is Double -> if (value > rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun ge(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value >= rhs.value) Bool.T else Bool.Nil
        is Double -> if (value >= rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }
}
